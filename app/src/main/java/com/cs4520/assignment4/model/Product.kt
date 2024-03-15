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
        val price: Double
    ): Product()
    data class Food(
        val name: String,
        val expiryDate: String?,
        val price: Double
    ): Product()

    companion object {
        fun create(name: Any?, type: Any?, expiryDate: Any?, price: Double?): Product? {
            if (type == "Food") {
                if (name != null) {
                    return price?.let { Food(name.toString(), expiryDate.toString(), it) }
                }

            } else {
                if (name != null) {
                    return price?.let { Equipment(name.toString(), expiryDate.toString(), it) }
                }

            }
            return null
        }
    }
}

