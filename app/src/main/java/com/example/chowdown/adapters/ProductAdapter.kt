package com.example.chowdown.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.R
import com.example.chowdown.models.Product

class ProductAdapter(
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivProductImage)
        val tvName: TextView = view.findViewById(R.id.tvProductName)
        val tvRating: TextView = view.findViewById(R.id.tvProductRating)
        val tvDesc: TextView = view.findViewById(R.id.tvProductDesc)
        val tvPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val btnOrder: Button = view.findViewById(R.id.btnOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvName.text = product.name
        holder.tvRating.text = "⭐ ${product.rating}"
        holder.tvDesc.text = product.description
        holder.tvPrice.text = String.format("R%.2f", product.price)
        
        holder.ivImage.setImageResource(product.imageResId)

        holder.itemView.setOnClickListener { onProductClick(product) }
        holder.btnOrder.setOnClickListener { onProductClick(product) }
    }

    override fun getItemCount(): Int = products.size

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}