package com.example.assignment_2_musicstudio

import Item
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val items = listOf(
        Item("Guitar", 5, "Acoustic", 100, R.drawable.guitar),
        Item("Drum Kit", 4, "Electronic", 150, R.drawable.drum),
        Item("Piano", 5, "Grand", 200, R.drawable.piano),
        Item("Violin", 3, "Electric", 120, R.drawable.violin)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUI()
    }

    private fun updateUI() {
        val item = items[currentIndex]
        findViewById<ImageView>(R.id.item_image).setImageResource(item.imageResId)
        findViewById<TextView>(R.id.item_name).text = item.name
        findViewById<RatingBar>(R.id.item_rating).rating = item.rating.toFloat()
        findViewById<TextView>(R.id.item_attribute).text = item.attributes.toString()
        findViewById<TextView>(R.id.item_price).text = "${item.price} credits"
    }

    fun onBorrowClicked(view: View) {
        val item = items[currentIndex]
        val currentRating = findViewById<RatingBar>(R.id.item_rating).rating.toInt()

        val updatedItem = item.copy(rating = currentRating)

        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("item_key", updatedItem)
        }
        startActivity(intent)
    }

    fun onNextClicked(view: View) {
        currentIndex = (currentIndex + 1) % items.size
        updateUI()
    }
}
