package com.example.mystoreonline.cart.domain

import com.example.mystoreonline.cart.data.CartRepository
import com.example.mystoreonline.cart.ui.model.CartModel
import com.example.mystoreonline.core.util.AddOrMinus
import javax.inject.Inject

class UpdateProductCartUseCase @Inject constructor(private val cartRepository: CartRepository){
    suspend operator fun invoke(cartModel: CartModel, addOrMinus: AddOrMinus){
        cartRepository.insertOrUpdate(cartModel, addOrMinus)
    }

}