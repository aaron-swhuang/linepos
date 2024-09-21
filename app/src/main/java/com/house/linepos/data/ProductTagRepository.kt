package com.house.linepos.data

import kotlinx.coroutines.flow.Flow

interface ProductTagRepository {
    suspend fun insert(tag: ProductTag)
    suspend fun update(tag: ProductTag)
    suspend fun delete(tag: ProductTag)
    suspend fun getTagById(id: Int): ProductTag?
    fun getAllTags(): Flow<List<ProductTag>>
    fun getActiveTags(): Flow<List<ProductTag>>
}