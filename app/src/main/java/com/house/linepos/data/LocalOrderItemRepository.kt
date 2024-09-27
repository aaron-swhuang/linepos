package com.house.linepos.data

import com.house.linepos.dao.OrderItemDao
import kotlinx.coroutines.flow.Flow

class LocalOrderItemRepository(private val orderItemDao: OrderItemDao) : OrderItemRepository {
    override suspend fun insert(item: OrderItem) {
        orderItemDao.insert(item)
    }

    override suspend fun update(item: OrderItem) {
        orderItemDao.update(item)
    }

    override suspend fun delete(item: OrderItem) {
        orderItemDao.delete(item)
    }

    override suspend fun getOrderById(id: Int): OrderItem {
        return orderItemDao.getOrderItemById(id)
    }

    override fun getAllOrders(): Flow<List<OrderItem>> {
        return orderItemDao.getAllOrderItems()
    }
}