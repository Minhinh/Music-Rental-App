package com.example.assignment_2_musicstudio

import Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private val TAG = "DetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        Log.i(TAG, "DetailActivity started")

        val item: Item? = intent.getParcelableExtra("item_key", Item::class.java)

        item?.let {
            findViewById<ImageView>(R.id.detail_image).setImageResource(it.imageResId)
            findViewById<TextView>(R.id.detail_name).text = it.name
            findViewById<RatingBar>(R.id.detail_rating).rating = it.rating.toFloat()
            findViewById<TextView>(R.id.detail_attribute).text = it.attributes.joinToString(", ") // Show only selected attributes
            findViewById<TextView>(R.id.detail_price).text = "${it.price} credits"

            Log.d(TAG, "Item displayed: ${it.name} with rating: ${it.rating}")
        }
    }

    fun onSaveClicked(view: View) {
        Log.i(TAG, "Save clicked, booking confirmed")

        // Create an intent to return result to MainActivity
        val resultIntent = Intent().apply {
            putExtra("result_message", "Item booked successfully!")
        }
        setResult(RESULT_OK, resultIntent)

        finish()  // Close the activity and return to the main screen
    }

    fun onCancelClicked(view: View) {
        Log.i(TAG, "Cancel clicked, booking cancelled")

        // Create an intent to return result to MainActivity
        val resultIntent = Intent().apply {
            putExtra("result_message", "Booking cancelled.")
        }
        setResult(RESULT_CANCELED, resultIntent)

        finish()  // Close the activity and return to the main screen
    }
}
