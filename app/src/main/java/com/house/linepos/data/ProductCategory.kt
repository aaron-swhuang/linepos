package com.house.linepos.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "product_category", indices = [Index(value = ["category"], unique = true)])
data class ProductCategory (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String = "",
    val description: String? = null,
    val isActive: Boolean = true
)