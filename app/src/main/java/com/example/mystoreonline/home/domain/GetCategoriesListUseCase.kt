package com.example.mystoreonline.home.domain

import com.example.mystoreonline.home.data.HomeRepository
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(private val repository: HomeRepository){
    suspend operator fun invoke(): List<String> {
        return repository.getCategories()
    }
}