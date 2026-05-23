package com.ekkyfish.data.repository

import com.ekkyfish.data.local.OrderDao
import com.ekkyfish.data.model.Order
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDao: OrderDao
) {

    fun getUserOrders(userId: String): Flow<List<Order>> {
        return orderDao.getUserOrders(userId)
    }

    suspend fun createOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    suspend fun updateOrder(order: Order) {
        orderDao.updateOrder(order)
    }

    suspend fun getOrderById(orderId: String): Order? {
        return orderDao.getOrderById(orderId)
    }
}