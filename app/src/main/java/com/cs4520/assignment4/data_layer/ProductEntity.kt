package com.cs4520.assignment4.data_layer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "expiry_date") val expiryDate: String?,
    val price: Double,
    val type: String
)

