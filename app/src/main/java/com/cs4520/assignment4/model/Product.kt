package com.cs4520.assignment4.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.lang.RuntimeException
import java.lang.reflect.Type



sealed class Product {

    data class Equipment(
        val name: String,
        val expiryDate: String?,
        val price: String
    ): Product()
    data class Food(
        val name: String,
        val expiryDate: String?,
        val price: String
    ): Product()

    companion object {
        fun create(name: Any?, type: Any?, expiryDate: Any?, price: Any?): Product? {
            if (type == "Food") {
                if (name != null) {
                    return price?.let { Product.Food(name.toString(), expiryDate.toString(), it.toString()) }
                }

            } else {
                if (name != null) {
                    return price?.let { Product.Equipment(name.toString(), expiryDate.toString(), it.toString()) }
                }

            }
            return null
        }
    }
}

