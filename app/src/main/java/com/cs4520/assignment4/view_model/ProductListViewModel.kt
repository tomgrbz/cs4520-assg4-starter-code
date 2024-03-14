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
import com.cs4520.assignment4.model.ProductDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListViewModel : ViewModel() {


    private val gson: Gson by lazy {
        val gson = GsonBuilder().registerTypeAdapter(Product::class.java, ProductDeserializer()).create()
        gson
    }
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }




    private val apiService = retrofit.create(IProductApi::class.java)

    private val repository = ProductRepository(apiService)
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun fetchProducts(page: Int) {
        viewModelScope.launch {
            try {
                Log.i("ProductListViewModel", "Fetching product list from API")
                val response = repository.getProducts(page)
                Log.i("PrdouctListViewModel", response.toString())
                _products.postValue(response)
            } catch (e: Exception) {
                // Handle error
                Log.e("Error: ProductListViewModel", e.toString())

            }
        }
    }
}



