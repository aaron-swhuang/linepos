package com.house.linepos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.house.linepos.data.LocalProductCategoryRepository
import com.house.linepos.data.LocalProductRepository
import com.house.linepos.data.LocalProductTagRepository
import com.house.linepos.data.Product
import com.house.linepos.data.ProductCategory
import com.house.linepos.data.ProductTag
import kotlinx.coroutines.launch

data class ProductDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val description: String? = null,
    val imagePath: String? = null,
    val isAvailable: Boolean = true,
    //val isActive: Boolean = true,
    //val isDiscontinued: Boolean = false,
    val tags: List<ProductTag>? = null,
    val category: ProductCategory? = null
)

class ProductViewModel(
    private val localProductRepository: LocalProductRepository,
    private val localProductCategoryRepository: LocalProductCategoryRepository,
    private val localProductTagRepository: LocalProductTagRepository
) : ViewModel() {
    val allProducts: LiveData<List<Product>> = localProductRepository.getAllProducts().asLiveData()
    val activeCategories: LiveData<List<ProductCategory>> =
        localProductCategoryRepository.getActiveCategories().asLiveData()
    val activeTags: LiveData<List<ProductTag>> =
        localProductTagRepository.getActiveTags().asLiveData()

    private val _productById = MutableLiveData<Product?>()
    val productById: LiveData<Product?> = _productById

    private val _productDetails = MutableLiveData<ProductDetails?>()
    val productDetails: LiveData<ProductDetails?> = _productDetails

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

    fun getProductDetails(id: Int) {
        viewModelScope.launch {
            val product = localProductRepository.getProductById(id)

            if (product != null) {
                val tags: MutableList<ProductTag> = mutableListOf()
                var category = ProductCategory()
                if (product.categoryId != null) {
                    category = localProductCategoryRepository.getCategoryById(product.categoryId)!!
                }
                if (product.tags != null) {
                    //val tags: MutableList<ProductTag> = mutableListOf()
                    product.tags.forEach { tag ->
                        val res = localProductTagRepository.getTagById(tag)
                        if (res != null) {
                            tags.add(res)
                        }
                    }
                }
               _productDetails.postValue(toProductDetails(product, category, tags))
            } else {
                _productDetails.postValue(null)
            }
        }
    }

    private fun toProductDetails(
        product: Product,
        category: ProductCategory,
        tags: List<ProductTag>): ProductDetails {
        return ProductDetails(
            id = product.id,
            name = product.name,
            price = product.price.toString(), // Double to String
            description = product.description,
            imagePath = product.imagePath,
            isAvailable = product.isAvailable,
            category = category,
            tags = tags
        )
    }
}