package com.example.mystoreonline.home.domain

import com.example.mystoreonline.core.util.sortProductsByOutStanding
import com.example.mystoreonline.home.data.HomeRepository
import com.example.mystoreonline.home.data.network.response.Product
import javax.inject.Inject

class GetProductsByCategoriesUseCase @Inject constructor(private val repository: HomeRepository){

    suspend operator fun invoke(category: String): List<Product> {
        return sortProductsByOutStanding(repository.getProductsByCategory(category))
    }

}