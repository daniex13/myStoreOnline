package com.example.mystoreonline.cart.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int = 1
)
