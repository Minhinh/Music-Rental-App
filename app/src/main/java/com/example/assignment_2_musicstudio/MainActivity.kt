package com.example.assignment_2_musicstudio

import Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var instrumentCondition = "Used"  // Default condition is "Used"
    private var rentalDays = 0

    // Listing all the items for the list, can be added more.
    private val items = listOf(
        Item("Guitar", 5, listOf("Acoustic", "Electric", "Wood"), 100, R.drawable.guitar),
        Item("Drum Kit", 4, listOf("Electronic", "Acoustic", "Wood"), 150, R.drawable.drum),
        Item("Piano", 5, listOf("Grand", "Digital", "Wood"), 200, R.drawable.piano),
        Item("Violin", 3, listOf("Electric", "Classical", "Wood"), 120, R.drawable.violin)
    )

    private var currentIndex = 0

    // Declare the ActivityResultLauncher
    private lateinit var detailActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Log the start of the MainActivity
        Log.i(TAG, "MainActivity started, displaying items")
        updateUI()

        // Initialize the ActivityResultLauncher
        detailActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val returnedPrice = result.data?.getIntExtra("price", 0) ?: 0
                rentalDays = result.data?.getIntExtra("rental_days", 0) ?: 0
                val message = result.data?.getStringExtra("result_message")

                // Update the price TextView in MainActivity with the returned price and rental days
                findViewById<TextView>(R.id.item_price).text = "$returnedPrice credits for $rentalDays days"

                message?.let {
                    Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG).show()
                }
            } else if (result.resultCode == RESULT_CANCELED) {
                val message = result.data?.getStringExtra("result_message")
                message?.let {
                    Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG).show()
                }
            }
        }

        // Handle RadioGroup for Used/New condition
        val radioGroupCondition = findViewById<RadioGroup>(R.id.radioGroupCondition)
        radioGroupCondition.setOnCheckedChangeListener { group, checkedId ->
            instrumentCondition = when (checkedId) {
                R.id.radio_used -> "Used"
                R.id.radio_new -> "New"
                else -> "Used"
            }
            Log.d(TAG, "Instrument condition selected: $instrumentCondition")
        }
    }

    private fun updateUI() {
        val item = items[currentIndex]
        findViewById<ImageView>(R.id.item_image).setImageResource(item.imageResId)
        findViewById<TextView>(R.id.item_name).text = item.name
        findViewById<RatingBar>(R.id.item_rating).rating = item.rating.toFloat()

        // Set the Chips for multi-choice attributes
        val chipGroup = findViewById<ChipGroup>(R.id.item_attribute_chipgroup)
        chipGroup.removeAllViews()  // Clear previous chips
        item.attributes.forEach { attribute ->
            val chip = Chip(this)
            chip.text = attribute
            chip.isCheckable = true  // Allow selection
            chipGroup.addView(chip)
        }

        findViewById<TextView>(R.id.item_price).text = "${item.price} credits"
        // Log for debugging values
        Log.d(TAG, "Current item displayed: ${item.name}")
    }

    fun onBorrowClicked(view: View) {
        val radioGroupCondition = findViewById<RadioGroup>(R.id.radioGroupCondition)
        val selectedConditionId = radioGroupCondition.checkedRadioButtonId

        // Validation to check if a condition is selected
        if (selectedConditionId == -1) {
            // No option selected, show error message
            Snackbar.make(view, "Please select 'Used' or 'New' condition.", Snackbar.LENGTH_SHORT).show()
            return
        }

        // Get the selected condition (Used or New)
        val selectedCondition = findViewById<RadioButton>(selectedConditionId).text.toString()

        val item = items[currentIndex]
        val currentRating = findViewById<RatingBar>(R.id.item_rating).rating.toInt()

        // Get the selected attributes from ChipGroup
        val selectedAttributes = mutableListOf<String>()
        val chipGroup = findViewById<ChipGroup>(R.id.item_attribute_chipgroup)
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedAttributes.add(chip.text.toString())
            }
        }

        // Error checking: Ensure at least one attribute is selected
        if (selectedAttributes.isEmpty()) {
            Snackbar.make(view, "Please select at least one attribute.", Snackbar.LENGTH_SHORT).show()
            return
        }

        val updatedItem = item.copy(rating = currentRating, attributes = selectedAttributes)  // Pass only selected attributes

        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("item_key", updatedItem)
            putExtra("condition", selectedCondition)  // Pass the condition (Used/New)
            putExtra("rental_days", rentalDays)  // Pass the rental days
        }
        detailActivityLauncher.launch(intent)

        Log.i(TAG, "Navigated to DetailActivity with item: ${item.name}, condition: $selectedCondition")
    }


    fun onNextClicked(view: View) {
        currentIndex = (currentIndex + 1) % items.size
        Log.d(TAG, "Next clicked, moving to item index: $currentIndex")
        updateUI()
    }
}
