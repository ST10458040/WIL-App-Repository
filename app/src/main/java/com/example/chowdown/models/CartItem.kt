package com.example.chowdown.models

import java.io.Serializable

data class CartItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val product: Product,
    val modifiers: List<Modifier>,
    var quantity: Int
): Serializable {
    val totalPrice: Double
        get() {
            val modifierTotal = modifiers.sumOf { it.price }
            return (product.price + modifierTotal) * quantity
        }
}