package com.cs4520.assignment4.application

import android.content.Context
import androidx.room.Room
import com.cs4520.assignment4.data_layer.LocalDatabase
import com.cs4520.assignment4.data_layer.Api
import com.cs4520.assignment4.data_layer.ProductRepository

class AppContainer {

    // The api client for fetching products from API endpoint
    private val remoteDataSource = Api.apiService
    // Client for local room database of products
    private lateinit var localDataSource: LocalDatabase

    lateinit var productRepository: ProductRepository // ProductRepository(remoteDataSource, localDataSource);

    var instance: Boolean = false

    fun createLocalDataSource(context: Context) {
        localDataSource = Room.databaseBuilder(context, LocalDatabase::class.java, "productsDB").build()
    }

    fun createProductRepository() {
        if (!instance && this::localDataSource.isInitialized) {
            productRepository = ProductRepository(remoteDataSource, localDataSource)
        }
    }

}