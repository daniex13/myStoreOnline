package com.example.mystoreonline.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mystoreonline.cart.data.CartDao
import com.example.mystoreonline.cart.data.CartEntity

@Database(entities = [CartEntity::class], version = 1)
abstract class MyStoreDataBase:RoomDatabase() {
    //Dao
    abstract fun cartDao(): CartDao
}