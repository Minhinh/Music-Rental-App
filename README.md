# Music Rental App - A music studio is leasing musical instruments and equipments

## Overview

This is a proof-of-concept Android application designed for a music studio that rents out musical instruments and equipment. The app allows clients to view available rental items, see details, and make bookings. It is implemented using Kotlin and traditional XML layouts.

## Features

- **View Rental Items:** Shows 3-4 rental items one at a time.
- **Item Details:** Displays name, rating, attribute, and price per month.
- **Next Item Navigation:** Navigate through items using the "Next" button.
- **Booking:** View detailed item information and save bookings.
- **Error Checking:** Ensures required details are filled before proceeding.
- **Feedback:** Uses Toast messages to confirm bookings or cancellations.

## Technologies

- Kotlin
- Android SDK
- XML Layouts
- Components of Intent
- Multi-Choice Widget using Chips
- State Persistence
- Parcelable for data passing

## Tesing and Debug

- Espresso
- Logcat .d .i .o

## Layouts

### Main Activity Layout

- Displays an image of the rental item.
- Shows item name, rating (using `RatingBar`), attribute, and price.
- Buttons for navigating to the next item and for booking the current item.

### Detail Activity Layout

- Displays detailed information about the selected rental item.
- Buttons for saving the booking or canceling it.

## Data Model

```kotlin
@Parcelize
data class RentalItem(
    val name: String,
    val rating: Float,
    val attribute: String,
    val pricePerMonth: Int
) : Parcelable
