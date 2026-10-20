package com.example.chowdown.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.R
import com.example.chowdown.models.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedIndex = 0

    init {
        if (categories.isNotEmpty()) {
            categories[0].isSelected = true
        }
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategory: CardView = view.findViewById(R.id.cvCategory)
        val ivIcon: ImageView = view.findViewById(R.id.ivCategoryIcon)
        val tvName: TextView = view.findViewById(R.id.tvCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.tvName.text = category.name
        holder.ivIcon.setImageResource(category.iconResId)

        if (position == selectedIndex) {
            holder.cvCategory.setCardBackgroundColor(Color.parseColor("#FF1A1A")) // Vibrant Red
            holder.ivIcon.setColorFilter(Color.WHITE)
            holder.tvName.setTextColor(Color.WHITE)
        } else {
            holder.cvCategory.setCardBackgroundColor(Color.parseColor("#1E1E1E")) // Surface color
            holder.ivIcon.setColorFilter(Color.GRAY)
            holder.tvName.setTextColor(Color.GRAY)
        }

        holder.itemView.setOnClickListener {
            val oldIndex = selectedIndex
            selectedIndex = holder.adapterPosition
            notifyItemChanged(oldIndex)
            notifyItemChanged(selectedIndex)
            onCategoryClick(category)
        }
    }

    override fun getItemCount(): Int = categories.size
}