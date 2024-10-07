package com.house.linepos.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import com.house.linepos.data.CartItem
import com.house.linepos.data.LocalOrderItemRepository
import com.house.linepos.data.LocalOrderRepository
import com.house.linepos.data.LocalProductCategoryRepository
import com.house.linepos.data.LocalProductRepository
import com.house.linepos.data.LocalProductTagRepository
import com.house.linepos.data.OrderMenuItemDetails
import com.house.linepos.data.Product
import com.house.linepos.data.ProductCategory
import com.house.linepos.data.ProductDetails
import com.house.linepos.data.ProductTag
import kotlinx.coroutines.launch
import java.text.DecimalFormat


class CartViewModel(
    private val localProductRepository: LocalProductRepository,
    private val localProductCategoryRepository: LocalProductCategoryRepository,
    private val localProductTagRepository: LocalProductTagRepository,
    private val localOrderRepository: LocalOrderRepository,
    private val localOrderItemRepository: LocalOrderItemRepository
) : ViewModel() {
    val allProducts: LiveData<List<Product>> = localProductRepository.getAvailableProducts().asLiveData()
    val activeCategories: LiveData<List<ProductCategory>> =
        localProductCategoryRepository.getActiveCategories().asLiveData()
    val activeTags: LiveData<List<ProductTag>> =
        localProductTagRepository.getActiveTags().asLiveData()

    // This is used to store current items in cart.
    private val _cartItems = MutableLiveData<List<OrderMenuItemDetails>>(emptyList())
    val cartItems: LiveData<List<OrderMenuItemDetails>> = _cartItems

    // This is used to get all available products to list in order menu screen.
    private val _orderMenuItems = MediatorLiveData<List<OrderMenuItemDetails>>()
    val orderMenuItems: LiveData<List<OrderMenuItemDetails>> = _orderMenuItems

    // MediatorLiveData to check if any item has been selected
    private val _hasSelectedItems = MediatorLiveData<Boolean>().apply {
        addSource(_orderMenuItems) { items ->
            value = items.any { (it.quantity.toIntOrNull() ?: 0) > 0 }
        }
    }
    val hasSelectedItems: LiveData<Boolean> = _hasSelectedItems

    init {
            _orderMenuItems.addSource(allProducts) { products ->
                viewModelScope.launch {
                _orderMenuItems.value = products.map { product ->
                    val tags: MutableList<ProductTag> = mutableListOf()
                    var category = ProductCategory()
                    if (product.categoryId != null) {
                        category =
                            localProductCategoryRepository.getCategoryById(product.categoryId)!!
                    }
                    if (product.tags != null) {
                        product.tags.forEach { tag ->
                            val res = localProductTagRepository.getTagById(tag)
                            if (res != null) {
                                tags.add(res)
                            }
                        }
                    }
                    val productDetails = toProductDetails(
                        product, category, tags)
                    OrderMenuItemDetails(
                        productDetails = productDetails,
                        quantity = "0"
                    )
                }
            }
        }
    }

    fun updateQuantity(productDetails: ProductDetails, newQuantity: String) {
        _orderMenuItems.value = _orderMenuItems.value?.map { item ->
            if (item.productDetails == productDetails) {
                item.copy(quantity = newQuantity)
            } else {
                item
            }
        }
    }

    fun addItemsToCart() {
            val selectedItems = _orderMenuItems.value?.filter {
                it.quantity.toInt() > 0
            } ?: emptyList()

            Log.e(TAG, "addItemsToCart")

            _cartItems.value = _cartItems.value?.plus(selectedItems)
            Log.d(TAG, "cartItems: ${_cartItems.value}")
            Log.d(TAG, "orderMenuItems: ${_orderMenuItems.value}")
            resetOrderMenuQuantity()
    }

    fun resetOrderMenuQuantity() {
        Log.e(TAG, "resetOrderMenuQuantity")
        _orderMenuItems.value = _orderMenuItems.value?.map {
            it.copy(quantity = "0")
        }

        val updatedItems = _orderMenuItems.value?.map {
            it.copy(quantity = "0") // reset quantity to 0
        } ?: emptyList()

        _orderMenuItems.value = updatedItems // update LiveData，trigger UI update
        Log.d(TAG, "orderMenuItems: ${_orderMenuItems.value}")
    }

    private fun toProductDetails(
        product: Product,
        category: ProductCategory,
        tags: List<ProductTag>): ProductDetails {

        val df = DecimalFormat("0.##")
        val price = df.format(product.price)

        return ProductDetails(
            id = product.id,
            name = product.name,
            price = price,
            description = product.description,
            imagePath = product.imagePath,
            isAvailable = product.isAvailable,
            category = category,
            tags = tags
        )
    }

    private fun toCartItem(cartItemDetails: OrderMenuItemDetails): CartItem {
        return CartItem(
            productDetails = cartItemDetails.productDetails,
            quantity = cartItemDetails.quantity.toInt()
        )
    }
}

/*
class CartViewModel(
    private val localProductRepository: LocalProductRepository,
    private val localProductCategoryRepository: LocalProductCategoryRepository,
    private val localProductTagRepository: LocalProductTagRepository,
    private val localOrderRepository: LocalOrderRepository,
    private val localOrderItemRepository: LocalOrderItemRepository
) : ViewModel() {
    // MutableLiveData save menu item
    private val _orderMenuItems = MutableLiveData<List<OrderMenuItemDetails>>()
    val orderMenuItems: LiveData<List<OrderMenuItemDetails>> get() = _orderMenuItems

    // MutableLiveData save cart item
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    init {
        // initial some fake data
        _orderMenuItems.value = listOf(
            OrderMenuItemDetails(ProductDetails(
                id = 1,
                name = "Product 1",
                price = "30.0",
                description = null,
                imagePath = "/data/user/0/com.house.linepos/files/1727552076026.jpg",
                isAvailable = true,
                tags = null,
                category = null), "0"),
            OrderMenuItemDetails(ProductDetails(
                id = 2,
                name = "Product 2",
                price = "10.0",
                description = null,
                imagePath = "/data/user/0/com.house.linepos/files/1727552076026.jpg",
                isAvailable = true,
                tags = null,
                category = null), "0")
        )
    }

    // update quantity of a item
    fun updateProductQuantity(productDetails: ProductDetails, newQuantity: Int) {
        Log.d(TAG, "updateProductQuantity")
        _orderMenuItems.value = _orderMenuItems.value?.map { item ->
            if (item.productDetails == productDetails) {
                item.copy(quantity = newQuantity.toString())
            } else {
                item
            }
        }
    }

    // add the selected item to cart and reset the quantity
    fun addToCartAndReset() {
        val selectedItems = _orderMenuItems.value?.filter { it.quantity.toInt() > 0 } ?: emptyList()
        Log.d(TAG, "addToCartAndReset")
        // convert the selected item to CartItem and add to cart
        val newCartItems = selectedItems.map {
            CartItem(it.productDetails, it.quantity.toInt())
        }
        _cartItems.value = _cartItems.value.orEmpty() + newCartItems
        Log.d(TAG, "_orderMenuItems: ${_orderMenuItems.value}")
        // reset all quantities to 0
        _orderMenuItems.value = _orderMenuItems.value?.map {
            it.copy(quantity = "0")
        }?.toList()
        Log.d(TAG, "_orderMenuItems: ${_orderMenuItems.value}")
    }
}
*/