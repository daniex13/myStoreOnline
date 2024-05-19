package com.example.mystoreonline.home.ui

import com.example.mystoreonline.home.data.network.response.Product

sealed class UiState {
    data object Loading : UiState()
    data class SuccessHome(
        val listProducts: List<Product>,val listCategory: List<String>,
    ) : UiState()
    data class Error(val message: String) : UiState()
}
