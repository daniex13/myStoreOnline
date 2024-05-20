package com.example.mystoreonline.home.domain

import com.example.mystoreonline.home.data.HomeRepository
import javax.inject.Inject

class GetAllProductsFromDataBase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(): Int {
        return if (homeRepository.getAllProductsFromDatabase().isNotEmpty()) {
            var quantity = 0
            homeRepository.getAllProductsFromDatabase().forEach {
                quantity += it.quantity
            }
            quantity
        } else {
            0
        }
    }

}