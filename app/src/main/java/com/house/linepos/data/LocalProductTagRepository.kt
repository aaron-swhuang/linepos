package com.house.linepos.data

import com.house.linepos.dao.ProductTagDao
import kotlinx.coroutines.flow.Flow

class LocalProductTagRepository(private val productTagDao: ProductTagDao) : ProductTagRepository {
    override suspend fun insert(tag: ProductTag) {
        productTagDao.insert(tag)
    }

    override suspend fun update(tag: ProductTag) {
        productTagDao.update(tag)
    }

    override suspend fun delete(tag: ProductTag) {
        productTagDao.delete(tag)
    }

    override suspend fun getTagById(id: Int): ProductTag? {
        return productTagDao.getTagById(id)
    }

    override fun getAllTags(): Flow<List<ProductTag>> {
        return productTagDao.getAllTags()
    }

    override fun getActiveTags(): Flow<List<ProductTag>> {
        return productTagDao.getActiveTags()
    }
}