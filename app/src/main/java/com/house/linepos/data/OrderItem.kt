package com.house.linepos.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "order_item",
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("orderId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("productId"),
            onDelete = ForeignKey.RESTRICT // Product will not be deleted if it is used in order.
        )
    ],
    indices = [Index(value = ["orderId"])]
)
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val unit: String?,
    val unitPrice: Double,
    val discount: Double?,
    val promotionId: Int?,
    val note: String?,
    val totalPrice: Double // quantity * unitPrice
)