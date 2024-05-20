package com.example.mystoreonline.cart.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.mystoreonline.core.util.AddOrMinus
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM CartEntity")
    suspend fun getAllProducts(): List<CartEntity>

    @Insert
    suspend fun insertProduct(product: CartEntity)

    @Update
    suspend fun updateProduct(product: CartEntity)

    @Upsert
    suspend fun insertOrUpdateProduct(product: CartEntity, addOrMinus: AddOrMinus) {
        val currentProduct = getAllProducts()
        if (currentProduct.find { product.id == it.id } != null) {
            if (addOrMinus == AddOrMinus.ADD)
                updateProduct(product.copy(quantity = currentProduct.find { product.id == it.id }!!.quantity + 1))
            if (addOrMinus == AddOrMinus.MINUS) {
                if (currentProduct.find { product.id == it.id }!!.quantity > 1)
                    updateProduct(product.copy(quantity = currentProduct.find { product.id == it.id }!!.quantity - 1))
                else
                    deleteProduct(product)
            }
        } else {
            insertProduct(product)
        }
    }

    @Delete
    suspend fun deleteProduct(product: CartEntity)

}