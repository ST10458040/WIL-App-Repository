package com.example.chowdown.models

import java.io.Serializable

data class Modifier(
    val id: String,
    val name: String,
    val price: Double,
    var isSelected: Boolean = false
): Serializable