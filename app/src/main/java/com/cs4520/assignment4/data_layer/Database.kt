package com.cs4520.assignment4.data_layer

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun productDao(): ProductDAO
}