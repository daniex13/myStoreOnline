package com.example.mystoreonline.core.util

import com.example.mystoreonline.home.data.network.response.Product

fun sortProductsByOutStanding(products: List<Product>): List<Product> {
    return products.sortedByDescending { it.rating.rate * it.rating.count }
}