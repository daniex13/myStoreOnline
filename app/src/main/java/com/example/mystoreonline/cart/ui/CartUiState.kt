package com.example.mystoreonline.cart.ui

import com.example.mystoreonline.cart.ui.model.CartModel

sealed interface CartUiState {
    data object Loading:CartUiState
    data class Error(val message: String):CartUiState
    data class Success(val cartList:List<CartModel>) :CartUiState
}