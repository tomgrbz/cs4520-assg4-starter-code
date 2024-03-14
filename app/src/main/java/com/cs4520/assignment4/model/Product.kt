package com.cs4520.assignment4.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

sealed class Product(
    @SerializedName("name")
    val name: String,
    @SerializedName("expiryDate")
    val expiryDate: String?,
    @SerializedName("price")
    val price: Int,
    @SerializedName("type")
    val type: String
) {

    data class Equipment(
        val equipmentName: String,
        val equipmentDate: String?,
        val equipmentPrice: Int
    ): Product(equipmentName, equipmentDate, equipmentPrice, "equipment")
    data class Food(
        val foodName: String,
        val foodDate: String,
        val foodPrice: Int,
    ): Product(foodName, foodDate, foodPrice, "food")

    companion object {
        fun create(name: String, type: String, expiryDate: String, price: Int): Product {
            if (type == "Equipment") {
                return Product.Equipment(name, type, price)
            } else {
                return Product.Food(name, type, price)
            }
        }
    }
}

class ProductDeserializer: JsonDeserializer<Product> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Product {
        if (json.isJsonObject) {

        }
        val jsonObj: JsonObject = json!!.asJsonObject

        return Product.create(jsonObj.get("name").asString, jsonObj.get("type").asString, jsonObj.get("expiryDate").asString, jsonObj.get("price").asInt)
    }

}