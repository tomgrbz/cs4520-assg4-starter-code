package com.cs4520.assignment4.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.R
import com.cs4520.assignment4.model.Product

class ProductListAdapter: RecyclerView.Adapter<ProductViewHolder>() {

    private lateinit var products: List<Product>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product, parent, false)
        return ProductViewHolder(view)
    }

    fun setList(productList: List<Product>) {
        products = productList
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

}

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
    private val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
    private val productExpiryDateTextView: TextView = itemView.findViewById(R.id.productExpiryDateTextView)
    private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)

    fun bind(product: Product) {
        productNameTextView.text = when(product) {
            is Product.Equipment -> product.name
            is Product.Food -> product.name
        }
        productPriceTextView.text = when(product) {
            is Product.Equipment -> "$" + product.price
            is Product.Food -> "$" + product.price
        }



        if (product is Product.Food) {
            productExpiryDateTextView.visibility = if (product.expiryDate != null) View.VISIBLE else View.GONE
            productExpiryDateTextView.text = product.expiryDate ?: ""
            itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.light_yellow, null))
            productImageView.setImageResource(R.drawable.food)
        } else if (product is Product.Equipment) {

            productExpiryDateTextView.visibility = View.GONE
            itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.light_red, null))
            productImageView.setImageResource(R.drawable.equipment)
        }
    }
}