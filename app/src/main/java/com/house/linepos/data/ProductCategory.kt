package com.house.linepos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_categories")
data class ProductCategory (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)