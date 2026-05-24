package com.ekkyfish.data.local

import com.ekkyfish.data.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderDao {
    fun getUserOrders(userId: String): Flow<List<Order>>

    suspend fun insertOrder(order: Order)

    suspend fun updateOrder(order: Order)

    suspend fun getOrderById(orderId: String): Order?
}
