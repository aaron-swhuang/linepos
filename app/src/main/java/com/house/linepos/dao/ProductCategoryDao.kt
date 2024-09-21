package com.house.linepos.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.house.linepos.data.ProductCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: ProductCategory)

    @Update
    suspend fun update(category: ProductCategory)

    @Delete
    suspend fun delete(category: ProductCategory)

    @Query("SELECT * FROM product_category WHERE id = :id")
    suspend fun getCategoryById(id: Int): ProductCategory?

    @Query("SELECT * FROM product_category ORDER BY category ASC")
    fun getAllCategories(): Flow<List<ProductCategory>>

    @Query("SELECT * FROM product_category WHERE isActive = 1")
    fun getActiveCategories(): Flow<List<ProductCategory>>
}