package com.example.mystoreonline.home.data

import com.example.mystoreonline.cart.data.CartDao
import com.example.mystoreonline.cart.data.CartEntity
import com.example.mystoreonline.cart.ui.model.CartModel
import com.example.mystoreonline.core.util.AddOrMinus
import com.example.mystoreonline.core.util.toData
import com.example.mystoreonline.home.data.network.HomeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeService,
    private val cartDao: CartDao
) {

    suspend fun getProducts() = api.getProducts()
    suspend fun getProductDetail(id: String) = api.getProductDetail(id)
    suspend fun getCategories() = api.getCategories()
    suspend fun getProductsByCategory(id: String) = api.getProductsByCategory(id)

    suspend fun getAllProductsFromDatabase(): List<CartModel> =
        cartDao.getAllProducts().map {
            CartModel(
                id = it.id,
                title = it.title,
                price = it.price,
                image = it.image,
                quantity = it.quantity
            )
        }


    suspend fun insertOrUpdateProductToDatabase(cartModel: CartModel, addOrMinus: AddOrMinus) = cartDao.insertOrUpdateProduct(
        cartModel.toData(), addOrMinus
    )

}