package com.house.linepos.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "product_categories", indices = [Index(value = ["category"], unique = true)])
data class ProductCategory (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "category")
    val category: String,
    val description: String? = null,
    val isActive: Boolean = true
)