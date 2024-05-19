package com.example.mystoreonline.home.data

import com.example.mystoreonline.home.data.network.HomeService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: HomeService) {

    suspend fun getProducts() = api.getProducts()
    suspend fun getProductDetail(id: String) = api.getProductDetail(id)
    suspend fun getCategories() = api.getCategories()
    suspend fun getProductsByCategory(id: String) = api.getProductsByCategory(id)


}