package com.example.chowdown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.adapters.OrderAdapter

class OrderStatusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_order_history, container, false)
        
        view.findViewById<View>(R.id.toolbar)?.visibility = View.GONE
        
        val llEmptyState = view.findViewById<View>(R.id.llEmptyState)
        val rvOrders = view.findViewById<RecyclerView>(R.id.rvOrders)

        view.findViewById<Button>(R.id.btnStartOrdering)?.setOnClickListener {
            (activity as? MainActivity)?.loadFragment(HomeFragment())
        }

        if (OrderManager.orders.isEmpty()) {
            llEmptyState?.visibility = View.VISIBLE
            rvOrders?.visibility = View.GONE
        } else {
            llEmptyState?.visibility = View.GONE
            rvOrders?.visibility = View.VISIBLE
            
            rvOrders?.adapter = OrderAdapter(OrderManager.orders) { order ->
                order.items.forEach { CartManager.addItem(it) }
                Toast.makeText(requireContext(), "Reordered items added to cart!", Toast.LENGTH_SHORT).show()
                (activity as? MainActivity)?.loadFragment(CartFragment())
            }
        }
        return view
    }
}