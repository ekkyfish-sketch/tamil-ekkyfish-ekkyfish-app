package com.ekkyfish.utils

object Constants {
    const val BASE_URL = "https://api.ekkyfish.com/"
    const val FIREBASE_DATABASE_URL = "https://ekkyfish.firebaseio.com"
    
    // Shared Preferences
    const val SHARED_PREFS_NAME = "ekkyfish_prefs"
    const val KEY_USER_ID = "user_id"
    const val KEY_USER_EMAIL = "user_email"
    const val KEY_USER_NAME = "user_name"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    
    // Product Categories
    val PRODUCT_CATEGORIES = listOf(
        "Freshwater Fish",
        "Saltwater Fish",
        "Frozen Fish",
        "Fish Fillets",
        "Seafood"
    )
    
    // Order Status
    const val ORDER_STATUS_PENDING = "pending"
    const val ORDER_STATUS_PROCESSING = "processing"
    const val ORDER_STATUS_SHIPPED = "shipped"
    const val ORDER_STATUS_DELIVERED = "delivered"
    const val ORDER_STATUS_CANCELLED = "cancelled"
}