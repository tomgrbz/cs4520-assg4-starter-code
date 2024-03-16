package com.cs4520.assignment4.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.application.ProductApplication
import com.cs4520.assignment4.databinding.ProductListFragmentBinding
import com.cs4520.assignment4.view_model.ProductListViewModel

class ProductListFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductListAdapter
    private lateinit var productListFragmentBinding: ProductListFragmentBinding
    private val productListViewModel: ProductListViewModel by viewModels { ProductListViewModel.Factory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        productListFragmentBinding = ProductListFragmentBinding.inflate(inflater, container, false)
        recyclerView = productListFragmentBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductListAdapter()
        recyclerView.adapter = adapter
        return productListFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Attempt to create app container to then instantiate ProductRepository to pass to Factory
        // for view model
        val appContainer = (activity?.application as ProductApplication).appContainer
        activity?.application?.applicationContext?.let { appContainer.createLocalDataSource(it) }
        activity?.application?.applicationContext?.let { appContainer.createProductRepository(it) }

        // Observe value for spinning progress bar
        productListViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            productListFragmentBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        // React to changes on product list
        observeProducts()
        productListViewModel.fetchProducts(1)
    }

    private fun observeProducts() {
        productListViewModel.products.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                Log.i("ProductFragment", "Seting list to $it")
                adapter.setList(it)
                productListFragmentBinding.progressBar.visibility = View.GONE
                productListFragmentBinding.txtMessage.visibility = View.GONE
                productListFragmentBinding.recyclerView.visibility = View.VISIBLE
            } else {
                productListFragmentBinding.recyclerView.visibility = View.GONE
                productListFragmentBinding.txtMessage.visibility = View.VISIBLE
            }
        })
    }


}