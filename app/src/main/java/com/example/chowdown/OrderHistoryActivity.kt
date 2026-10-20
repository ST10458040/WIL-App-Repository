package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.adapters.OrderAdapter
import com.example.chowdown.models.CartItem
import com.example.chowdown.models.Order
import com.example.chowdown.models.Product

class OrderHistoryActivity : AppCompatActivity() {

    private val sampleOrders = listOf(
        Order(
            id = "Order #72603109",
            date = "Jun 22, 2026, 07:55 PM",
            status = "PENDING",
            items = listOf(
                CartItem(
                    product = Product("5", "Salmon Nigiri Set", "Fresh Atlantic salmon on hand-pressed sushi rice", 120.0, 4.9, "25-35 min", R.drawable.img_1001777632, "sushi"),
                    modifiers = emptyList(),
                    quantity = 1
                )
            ),
            total = 135.0
        ),
        Order(
            id = "Order #7FBF39CF",
            date = "Jun 22, 2026, 07:54 PM",
            status = "PENDING",
            items = listOf(
                CartItem(
                    product = Product("5", "Salmon Nigiri Set", "Fresh Atlantic salmon on hand-pressed sushi rice", 120.0, 4.9, "25-35 min", R.drawable.img_1001777632, "sushi"),
                    modifiers = emptyList(),
                    quantity = 1
                )
            ),
            total = 135.0
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener { finish() }

        val rvOrders = findViewById<RecyclerView>(R.id.rvOrders)
        rvOrders?.adapter = OrderAdapter(sampleOrders) { order ->
            order.items.forEach { CartManager.addItem(it) }
            Toast.makeText(this, "Reordered item added to cart!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}