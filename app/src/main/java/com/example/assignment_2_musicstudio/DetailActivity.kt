package com.example.assignment_2_musicstudio

import Item
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item: Item? = intent.getParcelableExtra("item_key")

        item?.let {
            findViewById<ImageView>(R.id.detail_image).setImageResource(it.imageResId)
            findViewById<TextView>(R.id.detail_name).text = it.name
            findViewById<RatingBar>(R.id.detail_rating).rating = it.rating.toFloat()
            findViewById<TextView>(R.id.detail_attribute).text = it.attributes.toString()
            findViewById<TextView>(R.id.detail_price).text = "${it.price} credits"
        }
    }

    fun onSaveClicked(view: View) {
        Snackbar.make(view, "Item booked successfully!", Snackbar.LENGTH_LONG).show()
        finish()  // Close the activity and return to the main screen
    }

    fun onCancelClicked(view: View) {
        Snackbar.make(view, "Booking cancelled.", Snackbar.LENGTH_LONG).show()
        finish()  // Close the activity and return to the main screen
    }
}
