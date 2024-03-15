package com.cs4520.assignment4.data_layer

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.model.ProductResponse
import com.cs4520.assignment4.model.ProductToDAOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import java.lang.Exception

class ProductRepository(private val apiService: IProductApi, private val db: LocalDatabase, private val context: Context) {

    suspend fun getProducts(page: Int?): List<Product> {
        val setOfProducts: MutableSet<Product> = mutableSetOf()
        if (checkNetworkConn()) {
            val resp = apiService.getProducts(page)
            Log.i("ProductRepo", "Fetched records: ${resp.toString()}")
            if (resp.isSuccessful && resp.body() != null) {
                try {
                    // Fetch products from API and attempt to create valid Product representations
                    val respToProducts = resp.body()!!
                        .map { Product.create(it.name, it.type, it.expiryDate, it.price)!! }
                    // Filter out any duplicates from api
                    for (product in respToProducts) {
                        if (!setOfProducts.any {Product.compareProduct(product, it)}) {
                            setOfProducts.add(product)
                        }
                    }
                    // Insert set of products in DB
                    try {
                        withContext(Dispatchers.IO) {
                            insertProducts(setOfProducts.toList())
                        }
                    } catch (e: Exception) {
                        Log.e("ProductListViewModel", e.toString())
                    }
                    Log.i("ProductRepo", "Fetched records: original size: ${respToProducts.size} set size: ${setOfProducts.size} ")
                    return setOfProducts.toList()
                } catch (e: IllegalAccessException) {
                    Log.e("ProductRepo", "Failed to map products")
                } catch (e: Exception) {
                    Log.e("ProductRepo", "Failed to fetch any records from API")
                }

            }
        }
        else {
            Log.i("Product repo", "returning DB Records " )
            return withContext(Dispatchers.IO) {
                getProductsFromDB()
            }
        }

        return emptyList()
    }

    suspend fun getProductsFromDB(): List<Product> {
        val productEntities = db.productDao()
            .fetchAllProducts()
        return productEntities.map { ProductToDAOMapper.productEntityToProduct(it)!! }
    }


    suspend fun insertProducts(products: List<Product>) {
        db.productDao()
            .insertAllProducts(products.map { ProductToDAOMapper.productToProductEntity(it) })
    }

    private fun checkNetworkConn(): Boolean {
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetwork ?: return false
        val capabilities = connManager.getNetworkCapabilities(networkInfo) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }
}
