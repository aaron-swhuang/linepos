package com.house.linepos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.house.linepos.data.ProductTag
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductTagDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: ProductTag)

    @Update
    suspend fun update(tag: ProductTag)

    @Delete
    suspend fun delete(tag: ProductTag)

    @Query("SELECT * FROM product_tag WHERE id = :id")
    suspend fun getTagById(id: Int): ProductTag?

    @Query("SELECT * FROM product_tag ORDER BY name ASC")
    fun getAllTags(): Flow<List<ProductTag>>
}