package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.adapters.CartAdapter
import com.example.chowdown.models.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CartFragment : Fragment() {

    private lateinit var llEmptyState: View
    private lateinit var svPopulatedState: View
    private lateinit var tvSubtotal: TextView
    private lateinit var tvTotal: TextView
    private lateinit var bottomBar: View
    private val deliveryFee = 15.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        view.findViewById<View>(R.id.toolbar)?.visibility = View.GONE

        llEmptyState = view.findViewById(R.id.llEmptyState)
        svPopulatedState = view.findViewById(R.id.svPopulatedState)
        tvSubtotal = view.findViewById(R.id.tvSubtotal)
        tvTotal = view.findViewById(R.id.tvTotal)
        bottomBar = view.findViewById(R.id.bottomBar)

        view.findViewById<Button>(R.id.btnContinueShopping)?.setOnClickListener {
            (activity as? MainActivity)?.loadFragment(HomeFragment())
        }

        view.findViewById<Button>(R.id.btnCheckout)?.setOnClickListener {
            if (CartManager.items.isNotEmpty()) {
                val subtotal = CartManager.items.sumOf { it.totalPrice }
                val total = subtotal + deliveryFee
                val orderId = "Order #${System.currentTimeMillis().toString().takeLast(6)}"
                val sdf = SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.getDefault())
                val currentDate = sdf.format(Date())

                val newOrder = Order(
                    id = orderId,
                    date = currentDate,
                    status = "PREPPING",
                    items = CartManager.items.toList(),
                    total = total
                )

                OrderManager.addOrder(newOrder)
                CartManager.clearCart()

                Toast.makeText(requireContext(), "Order Placed Successfully!", Toast.LENGTH_LONG).show()
                updateCartUi()
                (activity as? MainActivity)?.updateOrderStatus()
                (activity as? MainActivity)?.updateCartBadge()
            }
        }

        setupCartList(view)
        updateCartUi()
        
        return view
    }

    private fun setupCartList(view: View) {
        val rvCartItems = view.findViewById<RecyclerView>(R.id.rvCartItems)
        rvCartItems?.adapter = CartAdapter(CartManager.items) {
            updateCartUi()
            (activity as? MainActivity)?.updateCartBadge()
        }
    }

    private fun updateCartUi() {
        if (CartManager.items.isEmpty()) {
            llEmptyState.visibility = View.VISIBLE
            svPopulatedState.visibility = View.GONE
            bottomBar.visibility = View.GONE
        } else {
            llEmptyState.visibility = View.GONE
            svPopulatedState.visibility = View.VISIBLE
            bottomBar.visibility = View.VISIBLE

            val subtotal = CartManager.items.sumOf { it.totalPrice }
            val total = subtotal + deliveryFee

            tvSubtotal.text = String.format("R%.2f", subtotal)
            tvTotal.text = String.format("R%.2f", total)
        }
    }
}