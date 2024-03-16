package com.cs4520.assignment4.data_layer

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 2)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
}