package com.example.mystoreonline.cart.ui.model

data class CartModel(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int = 1
)
