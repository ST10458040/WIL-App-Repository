package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.adapters.CartAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var llEmptyState: View
    private lateinit var svPopulatedState: View
    private lateinit var tvSubtotal: TextView
    private lateinit var tvTotal: TextView
    private val deliveryFee = 15.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        llEmptyState = findViewById(R.id.llEmptyState)
        svPopulatedState = findViewById(R.id.svPopulatedState)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvTotal = findViewById(R.id.tvTotal)

        findViewById<Button>(R.id.btnContinueShopping).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        findViewById<Button>(R.id.btnCheckout).setOnClickListener {
            Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_LONG).show()
            CartManager.clearCart()
            updateCartUi()
        }

        setupCartList()
        updateCartUi()
    }

    private fun setupCartList() {
        val rvCartItems = findViewById<RecyclerView>(R.id.rvCartItems)
        rvCartItems.adapter = CartAdapter(CartManager.items) {
            updateCartUi()
        }
    }

    private fun updateCartUi() {
        if (CartManager.items.isEmpty()) {
            llEmptyState.visibility = View.VISIBLE
            svPopulatedState.visibility = View.GONE
            findViewById<View>(R.id.bottomBar).visibility = View.GONE
        } else {
            llEmptyState.visibility = View.GONE
            svPopulatedState.visibility = View.VISIBLE
            findViewById<View>(R.id.bottomBar).visibility = View.VISIBLE

            val subtotal = CartManager.items.sumOf { it.totalPrice }
            val total = subtotal + deliveryFee

            tvSubtotal.text = String.format("R%.2f", subtotal)
            tvTotal.text = String.format("R%.2f", total)
        }
    }
}