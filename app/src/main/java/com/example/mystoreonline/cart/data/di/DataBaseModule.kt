package com.example.mystoreonline.cart.data.di

import android.content.Context
import androidx.room.Room
import com.example.mystoreonline.core.data.MyStoreDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideMyStoreDataBase(@ApplicationContext appContext: Context): MyStoreDataBase {
        return Room.databaseBuilder(appContext, MyStoreDataBase::class.java, "CartDataBase").build()
    }

    @Provides
    @Singleton
    fun provideCartDao(myStoreDataBase: MyStoreDataBase) = myStoreDataBase.cartDao()

}