package com.example.mystoreonline.home.data.network

import com.example.mystoreonline.home.data.network.response.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class HomeService @Inject constructor(private val homeClient: HomeClient) {

    suspend fun getProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getProducts()
            response.body() ?: emptyList()
        }
    }

    suspend fun getProductDetail(id: String): Product? {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getProductDetail(id)
            response.body()
        }
    }

    suspend fun getCategories(): List<String> {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getCategories()
            response.body() ?: emptyList()
        }
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getProductsByCategory(category)
            response.body() ?: emptyList()
        }
    }


}