package com.ekkyfish.data.model

data class Fish(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val unit: String = "kg", // kg, piece, dozen
    val imageUrl: String = "",
    val category: String = "", // Tilapia, Catfish, Prawn, etc
    val inStock: Boolean = true,
    val rating: Float = 4.5f,
    val reviews: Int = 0
)
