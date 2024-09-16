package com.house.linepos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.house.linepos.data.ProductCategory
import com.house.linepos.data.ProductCategoryRepository
import kotlinx.coroutines.launch

class ProductCategoryViewModel(private val productCategoryRepository: ProductCategoryRepository) : ViewModel() {
    val allCategories: LiveData<List<ProductCategory>> = productCategoryRepository.getAllCategories().asLiveData()

    fun insert(productCategory: ProductCategory) {
        viewModelScope.launch {
            productCategoryRepository.insert(productCategory)
        }
    }

    fun update(productCategory: ProductCategory) {
        viewModelScope.launch {
            productCategoryRepository.update(productCategory)
        }
    }

    fun delete(productCategory: ProductCategory) {
        viewModelScope.launch {
            productCategoryRepository.delete(productCategory)
        }
    }
}