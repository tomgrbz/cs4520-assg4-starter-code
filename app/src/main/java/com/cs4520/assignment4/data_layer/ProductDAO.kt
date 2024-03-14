package com.cs4520.assignment4.data_layer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDAO {
    @Query("SELECT * FROM products")
    fun fetchAllProducts(): List<ProductEntity>

    @Insert
    fun insertAllProducts(vararg products: ProductEntity)


}