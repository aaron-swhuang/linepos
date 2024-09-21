package com.house.linepos.data

import com.house.linepos.dao.ProductCategoryDao
import kotlinx.coroutines.flow.Flow

class LocalProductCategoryRepository(private val productCategoryDao: ProductCategoryDao)
    : ProductCategoryRepository {

    // Insert a new ProductCategory
    override suspend fun insert(category: ProductCategory) {
        productCategoryDao.insert(category)
    }

    // Update ProductCategory
    override suspend fun update(category: ProductCategory) {
        productCategoryDao.update(category)
    }

    // Delete ProductCategory
    override suspend fun delete(category: ProductCategory) {
        productCategoryDao.delete(category)
    }

    // Get a ProductCategory by Id
    override suspend fun getCategoryById(id: Int): ProductCategory? {
        return productCategoryDao.getCategoryById(id)
    }

    // Get all ProductCategory
    override fun getAllCategories(): Flow<List<ProductCategory>> {
        return productCategoryDao.getAllCategories()
    }

    override fun getActiveCategories(): Flow<List<ProductCategory >> {
        return productCategoryDao.getActiveCategories()
    }
}
