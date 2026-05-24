package com.ekkyfish.data.model

data class Order(
    val id: String = "",
    val userId: String = "",
    val items: List<CartItem> = emptyList(),
    val status: String = "pending", // pending, confirmed, shipped, delivered, cancelled
    val totalAmount: Double = 0.0,
    val deliveryAddress: String = "",
    val phone: String = "",
    val paymentMethod: String = "card", // card, upi, wallet, cod
    val paymentStatus: String = "pending", // pending, completed, failed
    val orderDate: Long = System.currentTimeMillis(),
    val deliveryDate: Long = 0,
    val specialInstructions: String = ""
)
