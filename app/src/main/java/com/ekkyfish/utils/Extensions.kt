package com.ekkyfish.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatDate(pattern: String = "MMM dd, yyyy"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}

fun Double.formatPrice(): String {
    return String.format("$%.2f", this)
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPhone(): Boolean {
    return this.length >= 10 && this.all { it.isDigit() }
}