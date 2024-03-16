package com.cs4520.assignment4.data_layer

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.model.ProductToDAOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Client consisting of local DB source and remote API source of data for a Product list
 */
class ProductRepository(
    private val apiService: IProductApi,
    private val db: LocalDatabase,
    private val context: Context
) {

    /**
     * Given a page as query param, attempts to fetch from API a list of products.
     * Checks for duplicates by creating a set and checking if a product has already been included
     *
     * Will insert latest API entries into local Room Database after duplication and data validation check.
     */
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
                        if (!setOfProducts.any { Product.compareProduct(product, it) }) {
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
                    return setOfProducts.toList()
                } catch (e: Exception) {
                    Log.e("ProductRepo", "Failed to fetch any records from API")
                }

            }
        } else {
            // There is no internet connection, so will return records from DB
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

    /**
     * Checks for internet connection
     */
    private fun checkNetworkConn(): Boolean {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetwork ?: return false
        val capabilities = connManager.getNetworkCapabilities(networkInfo) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }
}
