package com.house.linepos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.house.linepos.data.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("SELECT * From `order` WHERE id= :id")
    suspend fun getOrderById(id: Int): Order

    @Query("SELECT * FROM `order` ORDER BY id ASC")
    fun getAllOrders(): Flow<List<Order>>
}