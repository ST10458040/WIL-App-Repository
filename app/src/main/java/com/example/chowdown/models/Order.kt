package com.example.chowdown.models

data class Order(
    val id: String,
    val date: String,
    val status: String,
    val items: List<CartItem>,
    val total: Double
)