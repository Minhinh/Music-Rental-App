package com.example.assignment_2_musicstudio

import Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private val TAG = "DetailActivity"
    private var price = 0  // Initialize the price based on SeekBar
    private var rentalDays = 0  // Initialize rental days

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        Log.i(TAG, "DetailActivity started")
        // Get the condition from the intent
        val condition = intent.getStringExtra("condition") ?: "Unknown Condition"

        // Set the condition in the TextView
        findViewById<TextView>(R.id.detail_condition).text = "Condition: $condition"
        // Get the SeekBar and TextView for displaying price
        val seekBar = findViewById<SeekBar>(R.id.detail_seekbar)
        val seekBarValueTextView = findViewById<TextView>(R.id.detail_seekbar_value)
        val borrowDaysTextView = findViewById<TextView>(R.id.detail_price)

        // Set the SeekBar listener to update the price and rental days
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rentalDays = progress
                price = rentalDays * 100  // For example, 100 credits per level
                seekBarValueTextView.text = "$price credits"
                borrowDaysTextView.text = "Borrow for $rentalDays days"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Retrieve other item details
        val item: Item? = intent.getParcelableExtra("item_key", Item::class.java)
        item?.let {
            findViewById<ImageView>(R.id.detail_image).setImageResource(it.imageResId)
            findViewById<TextView>(R.id.detail_name).text = it.name
            findViewById<RatingBar>(R.id.detail_rating).rating = it.rating.toFloat()
            findViewById<TextView>(R.id.detail_attribute).text = it.attributes.joinToString(", ")
        }
    }

    // Save clicked: Return the updated credit value
    fun onSaveClicked(view: View) {
        Log.i(TAG, "Save clicked, booking confirmed with price: $price credits")

        // Create an intent to return result to MainActivity
        val resultIntent = Intent().apply {
            putExtra("price", price)  // Pass the updated price (credits) back
            putExtra("rental_days", rentalDays)  // Pass rental days
            putExtra("result_message", "Item booked successfully!")
        }
        setResult(RESULT_OK, resultIntent)
        finish()  // Close the DetailActivity and return to MainActivity
    }

    // Cancel clicked
    fun onCancelClicked(view: View) {
        Log.i(TAG, "Cancel clicked, booking cancelled")

        // Create an intent to return result to MainActivity
        val resultIntent = Intent().apply {
            putExtra("result_message", "Booking cancelled.")
        }
        setResult(RESULT_CANCELED, resultIntent)
        finish()  // Close the activity and return to MainActivity
    }
}

