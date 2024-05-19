package com.example.mystoreonline.home.domain

import com.example.mystoreonline.home.data.HomeRepository
import com.example.mystoreonline.home.data.network.response.Product
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(private val repository: HomeRepository){

    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }

}