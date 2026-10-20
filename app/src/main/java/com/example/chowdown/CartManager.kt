package com.example.chowdown

import com.example.chowdown.models.CartItem

object CartManager {
    val items = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        items.add(item)
    }

    fun clearCart() {
        items.clear()
    }
}