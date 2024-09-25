package com.house.linepos.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "product")
@Parcelize
data class Product (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val description: String? = null,
    val imagePath: String? = null,
    val isAvailable: Boolean = true,
    //val isActive: Boolean = true,
    //val isDiscontinued: Boolean = false,
    val tags: List<Int>? = null,
    // The product is not expected to be deleted if category does not exist.
    // Thus, categoryId does not need to be set as a foreign key.
    val categoryId: Int? = null
) : Parcelable