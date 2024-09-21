package com.house.linepos.data

import kotlinx.coroutines.flow.Flow

interface ProductCategoryRepository {
    suspend fun insert(productCategory: ProductCategory)
    suspend fun update(productCategory: ProductCategory)
    suspend fun delete(productCategory: ProductCategory)
    suspend fun getCategoryById(id: Int): ProductCategory?
    fun getAllCategories(): Flow<List<ProductCategory>>
    fun getActiveCategories(): Flow<List<ProductCategory>>
}