package com.house.linepos.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "product_category", indices = [Index(value = ["category"], unique = true)])
@Parcelize
data class ProductCategory (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String = "",
    val description: String? = null,
    val isActive: Boolean = true
) : Parcelable