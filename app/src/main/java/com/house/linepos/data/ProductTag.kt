package com.house.linepos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_tag")
data class ProductTag(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)