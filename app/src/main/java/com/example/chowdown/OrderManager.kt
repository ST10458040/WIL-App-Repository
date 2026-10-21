package com.example.chowdown

import com.example.chowdown.models.Order

object OrderManager {
    val orders = mutableListOf<Order>()

    fun addOrder(order: Order) {
        orders.add(0, order) // Add latest order to the top
    }

    fun clearOrders() {
        orders.clear()
    }
}