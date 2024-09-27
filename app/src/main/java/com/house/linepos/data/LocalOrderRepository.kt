package com.house.linepos.data

import com.house.linepos.dao.OrderDao
import kotlinx.coroutines.flow.Flow

class LocalOrderRepository(private val orderDao: OrderDao) : OrderRepository {
    override suspend fun insert(order: Order) {
        orderDao.insert(order)
    }

    override suspend fun update(order: Order) {
        orderDao.update(order)
    }

    override suspend fun delete(order: Order) {
        orderDao.delete(order)
    }

    override suspend fun getOrderById(id: Int): Order {
        return orderDao.getOrderById(id)
    }

    override fun getAllOrders(): Flow<List<Order>> {
        return orderDao.getAllOrders()
    }
}