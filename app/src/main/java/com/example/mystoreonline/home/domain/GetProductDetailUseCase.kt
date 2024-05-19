package com.example.mystoreonline.home.domain

import com.example.mystoreonline.home.data.HomeRepository
import com.example.mystoreonline.home.data.network.response.Product
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(private val repository: HomeRepository){

    suspend operator fun invoke(id: String): Product? {
        return repository.getProductDetail(id)
    }

}