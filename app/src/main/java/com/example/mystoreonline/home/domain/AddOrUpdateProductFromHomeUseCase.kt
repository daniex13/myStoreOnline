package com.example.mystoreonline.home.domain

import com.example.mystoreonline.core.util.AddOrMinus
import com.example.mystoreonline.core.util.toModelDatabase
import com.example.mystoreonline.home.data.HomeRepository
import com.example.mystoreonline.home.data.network.response.Product
import javax.inject.Inject

class AddOrUpdateProductFromHomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(product: Product) {
        homeRepository.insertOrUpdateProductToDatabase(product.toModelDatabase(), AddOrMinus.ADD)
    }

}



