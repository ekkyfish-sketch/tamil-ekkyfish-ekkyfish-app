package com.ekkyfish.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.ekkyfish.data.model.CartItem
import com.ekkyfish.data.model.Fish

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems: State<List<CartItem>> = _cartItems

    private val _totalPrice = mutableStateOf(0.0)
    val totalPrice: State<Double> = _totalPrice

    fun addToCart(fish: Fish, quantity: Int = 1) {
        val currentCart = _cartItems.value.toMutableList()
        val existingItem = currentCart.find { it.fish.id == fish.id }

        if (existingItem != null) {
            val index = currentCart.indexOf(existingItem)
            currentCart[index] = existingItem.copy(
                quantity = existingItem.quantity + quantity
            )
        } else {
            currentCart.add(CartItem(fish = fish, quantity = quantity))
        }

        _cartItems.value = currentCart
        updateTotalPrice()
    }

    fun removeFromCart(fishId: String) {
        _cartItems.value = _cartItems.value.filter { it.fish.id != fishId }
        updateTotalPrice()
    }

    fun updateQuantity(fishId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(fishId)
        } else {
            _cartItems.value = _cartItems.value.map { item ->
                if (item.fish.id == fishId) item.copy(quantity = quantity) else item
            }
            updateTotalPrice()
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        _totalPrice.value = 0.0
    }

    private fun updateTotalPrice() {
        _totalPrice.value = _cartItems.value.sumOf { it.fish.price * it.quantity }
    }

    fun getCartItemCount(): Int = _cartItems.value.sumOf { it.quantity }
}
