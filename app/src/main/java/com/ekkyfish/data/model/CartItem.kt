package com.ekkyfish.data.model

data class CartItem(
    val fish: Fish = Fish(),
    val quantity: Int = 1,
    val totalPrice: Double = 0.0
) {
    fun calculateTotal(): Double = fish.price * quantity
}
