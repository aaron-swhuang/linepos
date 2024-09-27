package com.house.linepos.data

import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun insert(order: Order)
    suspend fun update(order: Order)
    suspend fun delete(order: Order)
    suspend fun getOrderById(id: Int): Order
    fun getAllOrders(): Flow<List<Order>>
}