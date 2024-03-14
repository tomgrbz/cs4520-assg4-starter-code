package com.cs4520.assignment4.data_layer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDAO {
    @Query("SELECT * FROM products")
    fun fetchAllProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProducts(products: List<ProductEntity>)


}