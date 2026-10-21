package com.example.chowdown.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.R
import com.example.chowdown.models.Product

class ProductAdapter(
    private var products: MutableList<Product>,
    private val allProducts: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {

    private var currentCategory = "burgers"

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

    fun setCurrentCategory(category: String) {
        currentCategory = category
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.trim() ?: ""
                val filteredList = if (query.isEmpty()) {
                    allProducts.filter { it.categoryId == currentCategory }
                } else {
                    allProducts.filter {
                        it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true) ||
                        it.categoryId.contains(query, ignoreCase = true)
                    }
                }
                
                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filtered = results?.values as? List<Product> ?: emptyList()
                products.clear()
                products.addAll(filtered)
                notifyDataSetChanged()
            }
        }
    }
}