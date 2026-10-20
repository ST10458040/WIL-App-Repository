package com.example.chowdown.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.R
import com.example.chowdown.models.Order

class OrderAdapter(
    private val orders: List<Order>,
    private val onReorderClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tvOrderId)
        val tvDate: TextView = view.findViewById(R.id.tvOrderDate)
        val tvStatus: TextView = view.findViewById(R.id.tvOrderStatus)
        val ivOrderImage: ImageView = view.findViewById(R.id.ivOrderImage)
        val tvItemName: TextView = view.findViewById(R.id.tvOrderItemName)
        val tvItemPrice: TextView = view.findViewById(R.id.tvOrderItemPrice)
        val tvQuantity: TextView = view.findViewById(R.id.tvOrderQuantity)
        val tvTotal: TextView = view.findViewById(R.id.tvOrderTotal)
        val btnReorder: Button = view.findViewById(R.id.btnReorder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.tvId.text = order.id
        holder.tvDate.text = order.date
        holder.tvStatus.text = order.status
        
        if (order.items.isNotEmpty()) {
            val firstItem = order.items.first()
            holder.tvItemName.text = firstItem.product.name
            holder.tvItemPrice.text = String.format("R%.2f", firstItem.totalPrice)
            holder.tvQuantity.text = "Qty: ${firstItem.quantity}"
            if (firstItem.product.imageResId != 0) {
                holder.ivOrderImage.setImageResource(firstItem.product.imageResId)
            }
        }
        
        holder.tvTotal.text = String.format("R%.2f", order.total)
        holder.btnReorder.setOnClickListener { onReorderClick(order) }
    }

    override fun getItemCount(): Int = orders.size
}