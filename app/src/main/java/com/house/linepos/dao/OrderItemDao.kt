package com.house.linepos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.house.linepos.data.OrderItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: OrderItem)

    @Update
    suspend fun update(item: OrderItem)

    @Delete
    suspend fun delete(item: OrderItem)

    @Query("SELECT * FROM order_item WHERE id= :id")
    suspend fun getOrderItemById(id: Int): OrderItem

    @Query("SELECT * FROM order_item ORDER BY id")
    fun getAllOrderItems(): Flow<List<OrderItem>>
}