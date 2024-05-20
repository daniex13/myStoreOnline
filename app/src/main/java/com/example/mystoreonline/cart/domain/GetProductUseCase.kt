package com.example.mystoreonline.cart.domain

import com.example.mystoreonline.cart.data.CartRepository
import com.example.mystoreonline.cart.ui.model.CartModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val cartRepository: CartRepository) {

    suspend operator fun invoke(): List<CartModel> = cartRepository.cartProduct()

}