package com.house.linepos.data

// Class used to store in database
data class CartItem(
    val productDetails: ProductDetails,
    val quantity: Int = 0
)

// Class to show data on screen
data class OrderMenuItemDetails(
    val productDetails: ProductDetails,
    val quantity: String = ""
)