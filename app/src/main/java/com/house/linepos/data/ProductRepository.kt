package com.house.linepos.data

import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insert(product: Product)
    suspend fun update(product: Product)
    suspend fun delete(product: Product)
    suspend fun getProductById(id: Int): Product?
    fun getAllProducts(): Flow<List<Product>>
    fun getProductsByCategoryId(): Flow<List<Product>>
}