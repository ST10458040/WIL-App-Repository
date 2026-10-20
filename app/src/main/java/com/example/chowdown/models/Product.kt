package com.example.chowdown.models

import java.io.Serializable

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val deliveryTime: String,
    val imageResId: Int,
    val categoryId: String
): Serializable