package com.house.linepos.data

import com.house.linepos.dao.ProductCategoryDao
import kotlinx.coroutines.flow.Flow

// TODO: This can be a interface and have another class that implements the functions.
//       Then define a offline/local repository for saving the data at local database and
//       define a online/remote repository for saving the data to remote database.
class ProductCategoryRepository(private val productCategoryDao: ProductCategoryDao) {

    // Insert a new ProductCategory
    suspend fun insert(category: ProductCategory) {
        productCategoryDao.insert(category)
    }

    // Update ProductCategory
    suspend fun update(category: ProductCategory) {
        productCategoryDao.update(category)
    }

    // Delete ProductCategory
    suspend fun delete(category: ProductCategory) {
        productCategoryDao.delete(category)
    }

    // Get a ProductCategory by Id
    suspend fun getCategoryById(id: Int): ProductCategory? {
        return productCategoryDao.getCategoryById(id)
    }

    // Get all ProductCategory
    fun getAllCategories(): Flow<List<ProductCategory>> {
        return productCategoryDao.getAllCategories()
    }
}
