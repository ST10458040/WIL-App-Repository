package com.example.chowdown.models

data class Message(
    val id: String,
    val title: String,
    val body: String,
    val time: String,
    val type: String // "Orders", "Updates", "Vouchers"
)