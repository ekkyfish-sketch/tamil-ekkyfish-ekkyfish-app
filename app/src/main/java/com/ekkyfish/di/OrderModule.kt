package com.ekkyfish.di

import com.ekkyfish.data.local.OrderDao
import com.ekkyfish.data.model.Order
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderModule {

    @Provides
    @Singleton
    fun provideOrderDao(): OrderDao {
        return object : OrderDao {
            override fun getUserOrders(userId: String): Flow<List<Order>> = emptyFlow()

            override suspend fun insertOrder(order: Order) {
                // No-op placeholder implementation for initial emulator build.
            }

            override suspend fun updateOrder(order: Order) {
                // No-op placeholder implementation for initial emulator build.
            }

            override suspend fun getOrderById(orderId: String): Order? = null
        }
    }
}
