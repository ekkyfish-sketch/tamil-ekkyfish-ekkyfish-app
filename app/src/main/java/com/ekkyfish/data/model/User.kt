package com.ekkyfish.data.model

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
    val profileImageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)