package com.cs4520.assignment4.model

import com.cs4520.assignment4.data_layer.ProductEntity
import java.lang.IllegalArgumentException

/**
 * Mapper class consisting of two static functions to map from Product to ProductEntity and
 * from ProductEntity to Product
 */
class ProductToDAOMapper {

    companion object {
        /**
         * Maps a Product type of either Food or Equipment to a Product Entity for db entry
         */
        fun productToProductEntity(product: Product): ProductEntity {
            return if (product is Product.Food) {
                ProductEntity(name = product.name, type = "Food", price = product.price, expiryDate = product.expiryDate)
            } else if (product is Product.Equipment){
                ProductEntity(name = product.name, type = "Equipment", price = product.price, expiryDate = product.expiryDate)
            } else {
                throw IllegalArgumentException("Invalid product given to mapper")
            }

        }

        fun productEntityToProduct(productEntity: ProductEntity): Product? {
            return if (productEntity.type == "Food") {
                Product.create(productEntity.name, "Food", productEntity.expiryDate, productEntity.price)
            } else if (productEntity.type == "Equipment") {
                Product.create(productEntity.name, "Equipment", productEntity.expiryDate, productEntity.price)
            } else {
                throw IllegalArgumentException("Invalid product entity given to mapper")
            }
        }
    }
}