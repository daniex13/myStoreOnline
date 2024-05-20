package com.example.mystoreonline.cart.domain

import com.example.mystoreonline.cart.data.CartRepository
import com.example.mystoreonline.cart.ui.model.CartModel
import javax.inject.Inject

class DeleteProductCartUseCase @Inject constructor(private val cartRepository: CartRepository){
    suspend operator fun invoke(cartModel: CartModel){
        cartRepository.delete(cartModel)
    }

}