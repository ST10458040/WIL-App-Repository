package com.example.chowdown.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.R
import com.example.chowdown.models.CartItem

class CartAdapter(
    private var cartItems: MutableList<CartItem>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCartImage: ImageView = view.findViewById(R.id.ivCartImage)
        val tvName: TextView = view.findViewById(R.id.tvCartItemName)
        val tvModifiers: TextView = view.findViewById(R.id.tvCartItemModifiers)
        val tvPrice: TextView = view.findViewById(R.id.tvCartItemPrice)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
        val btnPlus: TextView = view.findViewById(R.id.btnPlus)
        val btnMinus: TextView = view.findViewById(R.id.btnMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.tvName.text = item.product.name
        
        if (item.product.imageResId != 0) {
            holder.ivCartImage.setImageResource(item.product.imageResId)
        }
        
        val modifierText = item.modifiers.filter { it.isSelected }.joinToString(", ") { it.name }
        holder.tvModifiers.text = if (modifierText.isNotEmpty()) "+ $modifierText" else ""
        
        holder.tvPrice.text = String.format("R%.2f", item.totalPrice)
        holder.tvQuantity.text = item.quantity.toString()

        holder.btnPlus.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onCartUpdated()
        }

        holder.btnMinus.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
            } else {
                cartItems.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cartItems.size)
            }
            onCartUpdated()
        }
    }

    override fun getItemCount(): Int = cartItems.size
}