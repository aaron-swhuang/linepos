package com.house.linepos.data

import kotlinx.coroutines.flow.Flow

interface OrderItemRepository {
    suspend fun insert(item: OrderItem)
    suspend fun update(item: OrderItem)
    suspend fun delete(item: OrderItem)
    suspend fun getOrderById(id: Int): OrderItem
    fun getAllOrders(): Flow<List<OrderItem>>
}