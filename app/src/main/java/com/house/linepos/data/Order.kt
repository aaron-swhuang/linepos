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
    val customerId: Int?,
    val createAt: Date,
    val totalAmount: Double
)