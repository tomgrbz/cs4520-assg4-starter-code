package com.cs4520.assignment4.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment4.data_layer.Api
import com.cs4520.assignment4.data_layer.IProductApi
import com.cs4520.assignment4.data_layer.ProductRepository
import com.cs4520.assignment4.model.Product
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListViewModel : ViewModel() {


    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun fetchProducts(page: Int?) {


        val repository = ProductRepository(Api.apiService)
        viewModelScope.launch {
            try {
                Log.i("ProductListViewModel", "Fetching product list from API")
                val response: List<Product> =
                    repository.getProducts(page)
                        .map { Product.create(it.name, it.type, it.expiryDate, it.price)!! }
                Log.i("PrdouctListViewModel", response.toString())
                _products.postValue(response)
            } catch (e: Exception) {
                // Handle error
                Log.e("Error: ProductListViewModel", e.toString())
                _products.postValue(emptyList())

            }
        }
    }
}



