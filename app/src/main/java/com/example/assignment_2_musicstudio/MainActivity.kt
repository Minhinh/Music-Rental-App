package com.example.assignment_2_musicstudio

import Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val REQUEST_CODE_DETAIL = 1

    private val items = listOf(
        Item("Guitar", 5, listOf("Acoustic", "Electric"), 100, R.drawable.guitar),
        Item("Drum Kit", 4, listOf("Electronic", "Acoustic"), 150, R.drawable.drum),
        Item("Piano", 5, listOf("Grand", "Digital"), 200, R.drawable.piano),
        Item("Violin", 3, listOf("Electric", "Classical"), 120, R.drawable.violin)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "MainActivity started, displaying items")
        updateUI()
    }

    private fun updateUI() {
        val item = items[currentIndex]
        findViewById<ImageView>(R.id.item_image).setImageResource(item.imageResId)
        findViewById<TextView>(R.id.item_name).text = item.name
        findViewById<SeekBar>(R.id.item_rating_seekbar).progress = item.rating

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

        Log.d(TAG, "Current item displayed: ${item.name}")
    }

    fun onBorrowClicked(view: View) {
        val item = items[currentIndex]
        val currentRating = findViewById<SeekBar>(R.id.item_rating_seekbar).progress

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

        // Error checking: Check credit limit (for simplicity, let's assume 500 credits are available)
        if (item.price > 500) {
            Snackbar.make(view, "You do not have enough credits.", Snackbar.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Borrow clicked for ${item.name} with updated rating: $currentRating")

        val updatedItem = item.copy(rating = currentRating)

        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("item_key", updatedItem)
        }
        startActivityForResult(intent, REQUEST_CODE_DETAIL)

        Log.i(TAG, "Navigated to DetailActivity with item: ${item.name}")
    }

    fun onNextClicked(view: View) {
        currentIndex = (currentIndex + 1) % items.size
        Log.d(TAG, "Next clicked, moving to item index: $currentIndex")
        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_DETAIL) {
            data?.let {
                val message = it.getStringExtra("result_message")
                message?.let {
                    Log.i(TAG, "Displaying result message: $message")
                    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
