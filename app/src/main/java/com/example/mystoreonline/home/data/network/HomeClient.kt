package com.example.mystoreonline.home.data.network

import com.example.mystoreonline.home.data.network.response.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeClient {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: String): Response<Product>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<List<Product>>
}