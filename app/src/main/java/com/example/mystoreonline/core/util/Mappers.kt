package com.example.mystoreonline.core.util

import com.example.mystoreonline.cart.data.CartEntity
import com.example.mystoreonline.cart.ui.model.CartModel
import com.example.mystoreonline.home.data.network.response.Product

fun Product.toModelDatabase(): CartModel {
    return CartModel(id = this.id.toInt(),
        title = this.title,
        price = this.price,
        image = this.image)
}

fun CartModel.toData(): CartEntity {
    return CartEntity(id = this.id,
        title = this.title,
        price = this.price,
        image = this.image)
}