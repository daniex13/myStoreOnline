package com.example.mystoreonline.cart.data

import com.example.mystoreonline.cart.ui.model.CartModel
import com.example.mystoreonline.core.util.AddOrMinus
import com.example.mystoreonline.core.util.toData
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartDao: CartDao) {
    suspend fun cartProduct(): List<CartModel> = cartDao.getAllProducts().map {
        CartModel(
            id = it.id,
            title = it.title,
            price = it.price,
            image = it.image,
            quantity = it.quantity
        )
    }


    suspend fun insert(cartModel: CartModel) = cartDao.insertProduct(
        cartModel.toData()
    )

    suspend fun update(cartModel: CartModel) = cartDao.updateProduct(
        cartModel.toData()
    )

    suspend fun insertOrUpdate(cartModel: CartModel, addOrMinus: AddOrMinus) = cartDao.insertOrUpdateProduct(
        cartModel.toData(), addOrMinus
    )

    suspend fun delete(cartModel: CartModel) = cartDao.deleteProduct(
        cartModel.toData()
    )

}

