package com.house.linepos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imagePath: String?,
    val isAvailable: Boolean,
    val tags: List<Int>?,
    val categoryId: Int
)