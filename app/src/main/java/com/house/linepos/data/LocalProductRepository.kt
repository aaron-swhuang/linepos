package com.house.linepos.data

import com.house.linepos.dao.ProductDao
import kotlinx.coroutines.flow.Flow

class LocalProductRepository(private val productDao: ProductDao) : ProductRepository {
    override suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    override suspend fun update(product: Product) {
        productDao.update(product)
    }

    override suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    override suspend fun getProductById(id: Int): Product? {
        return productDao.getProductById(id)
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    override fun getProductsByCategoryId(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }
}