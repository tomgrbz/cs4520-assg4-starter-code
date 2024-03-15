package com.cs4520.assignment4.view_model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.room.Room
import com.cs4520.assignment4.application.ProductApplication
import com.cs4520.assignment4.data_layer.Api
import com.cs4520.assignment4.data_layer.IProductApi
import com.cs4520.assignment4.data_layer.ProductRepository
import com.cs4520.assignment4.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {


    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    fun fetchProducts(page: Int?) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                Log.i("ProductListViewModel", "Fetching product list from API")
                val response: List<Product> =
                    repository.getProducts(page)
                Log.i("ProductListViewModel", response.toString())
                _products.postValue(response)
                try {
                    withContext(Dispatchers.IO) {
                        repository.insertProducts(response)
                    }
                } catch (e: Exception) {
                    Log.e("ProductListViewModel", e.toString())
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("Error: ProductListViewModel", e.toString())
                _products.postValue(emptyList())

            } finally {
                _isLoading.postValue(false)
            }

        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application = checkNotNull(extras[APPLICATION_KEY])

                val savedStateHandle = extras.createSavedStateHandle()
                return ProductListViewModel((application as ProductApplication).appContainer.productRepository,) as T
            }
        }
    }
}



