package com.cs4520.assignment4.data_layer

import android.util.Log
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.model.ProductResponse
import retrofit2.awaitResponse

class ProductRepository(private val apiService: IProductApi, db: Database) {

     suspend fun getProducts(page: Int?): List<ProductResponse> {

        val resp = apiService.getProducts(page)
         Log.i("ProductRepo", "Fetched records: ${resp.toString()}")
         if (resp.isSuccessful && resp.body() != null) {
             return resp.body()!!
         }
         Log.e("ProductRepo", "Failed to fetch any records from API")
        return emptyList()
    }

    private fun insertProducts(products: List<Product>) {
        db.productDao()
    }
}
