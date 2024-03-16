package com.cs4520.assignment4.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.cs4520.assignment4.application.ProductApplication
import com.cs4520.assignment4.data_layer.ProductRepository
import com.cs4520.assignment4.model.Product
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {


    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun fetchProducts(page: Int?) {
        // Loading bar should start to spin
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Fetch from API, on failure will return DB records instead to display in View
                val response: List<Product> =
                    repository.getProducts(page)
                _products.value = response
            } catch (e: Exception) {
                // Handle error, setting value of list to empty
                Log.e("Error: ProductListViewModel", e.toString())
                _products.value = emptyList()

            } finally {
                // On failure or success, sets loading bar to stop
                _isLoading.value = false
            }

        }
    }

    /**
     * Factory companion object to instantiate in the ProductFragmentList
     */
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application = checkNotNull(extras[APPLICATION_KEY])
                return ProductListViewModel((application as ProductApplication).appContainer.productRepository) as T
            }
        }
    }
}



