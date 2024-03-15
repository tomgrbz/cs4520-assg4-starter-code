package com.cs4520.assignment4.data_layer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "products", indices = [Index(value = ["name", "price"], unique = true)])
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "expiry_date") val expiryDate: String?,
    val price: Double,
    val type: String
)

