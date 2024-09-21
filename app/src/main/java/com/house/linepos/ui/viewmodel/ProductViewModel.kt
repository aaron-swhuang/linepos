package com.house.linepos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.house.linepos.data.LocalProductRepository
import com.house.linepos.data.LocalProductTagRepository
import com.house.linepos.data.Product
import com.house.linepos.data.ProductCategory
import com.house.linepos.data.ProductCategoryRepository
import com.house.linepos.data.ProductTag
import kotlinx.coroutines.launch

class ProductViewModel(
    private val localProductRepository: LocalProductRepository,
    private val productCategoryRepository: ProductCategoryRepository,
    private val localProductTagRepository: LocalProductTagRepository
) : ViewModel() {
    val allProducts: LiveData<List<Product>> = localProductRepository.getAllProducts().asLiveData()
    val activeCategories: LiveData<List<ProductCategory>> =
        productCategoryRepository.getActiveCategories().asLiveData()
    val activeTags: LiveData<List<ProductTag>> =
        localProductTagRepository.getActiveTags().asLiveData()

    private val _productById = MutableLiveData<Product?>()
    val productById: LiveData<Product?> = _productById
    // TODO: Consider StateFlow or LiveData
    /*
    // Use StateFlow
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product
     */

    fun insert(product: Product) {
        viewModelScope.launch {
            localProductRepository.insert(product)
        }
    }

    fun update(product: Product) {
        viewModelScope.launch {
            localProductRepository.update(product)
        }
    }

    fun delete(product: Product) {
        viewModelScope.launch {
            localProductRepository.delete(product)
        }
    }

    fun getProductById(id: Int) {
        viewModelScope.launch {
            val res = localProductRepository.getProductById(id)

            // update LiveData
            _productById.postValue(res)
            /*
            // or update StateFlow
            _product.value = result
            */
        }
    }
}