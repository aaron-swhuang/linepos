package com.house.linepos.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "order")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dailyOrderId: Int,
    val orderType: String, //'Dine-in', 'Takeout', 'Delivery', etc.
    val orderStatus: String,
    val customerId: Int?,
    val address: String?,
    val discount: Double?, // For entire order
    val taxAmount: Double?,
    val createdAt: Date,
    val updatedAt: Date,
    val completedAt: Date,
    val paymentMethod: String,
    val paymentStatus: String,
    val note: String?,
    val totalAmount: Double
)