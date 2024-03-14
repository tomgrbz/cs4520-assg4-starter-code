package com.cs4520.assignment4.data_layer

import android.util.Log
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.model.ProductResponse
import com.cs4520.assignment4.model.ProductToDAOMapper
import retrofit2.awaitResponse
import java.lang.Exception

class ProductRepository(private val apiService: IProductApi, private val db: LocalDatabase) {

    suspend fun getProducts(page: Int?): List<Product> {

        val resp = apiService.getProducts(page)
        Log.i("ProductRepo", "Fetched records: ${resp.toString()}")
        if (resp.isSuccessful && resp.body() != null) {
            try {
                val respToProducts = resp.body()!!
                    .map { Product.create(it.name, it.type, it.expiryDate, it.price)!! }
                // insertProducts(respToProducts)
                return respToProducts
            } catch (e: IllegalAccessException) {
                Log.e("ProductRepo", "Failed to map products")
            } catch (e: Exception) {
                Log.e("ProductRepo", "Failed to fetch any records from API")
            }

        }

        return emptyList()
    }

    fun getProductsFromDB(): List<Product> {
        val productEntities = db.productDao()
            .fetchAllProducts()
        return productEntities.map { ProductToDAOMapper.productEntityToProduct(it)!! }
    }


    fun insertProducts(products: List<Product>) {
        db.productDao()
            .insertAllProducts(products.map { ProductToDAOMapper.productToProductEntity(it) })
    }
}
