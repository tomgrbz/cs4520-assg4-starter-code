package com.cs4520.assignment4.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.databinding.ProductListFragmentBinding
import com.cs4520.assignment4.view_model.ProductListViewModel

class ProductListFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductListAdapter
    private lateinit var productListFragmentBinding: ProductListFragmentBinding
    private lateinit var productListViewModel: ProductListViewModel


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
        productListViewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

        productListFragmentBinding.btnRefresh.setOnClickListener {
            productListViewModel.fetchProducts(1) // Fetch products for page 1
        }
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
                productListFragmentBinding.progressBar.visibility = View.GONE
                productListFragmentBinding.recyclerView.visibility = View.GONE
                productListFragmentBinding.txtMessage.visibility = View.VISIBLE
                productListFragmentBinding.txtMessage.text = "No products available"
            }
        })
    }


}