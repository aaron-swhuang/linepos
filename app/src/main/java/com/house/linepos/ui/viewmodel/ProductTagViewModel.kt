package com.house.linepos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.house.linepos.data.LocalProductTagRepository
import com.house.linepos.data.ProductTag
import kotlinx.coroutines.launch

class ProductTagViewModel(private val localProductTagRepository: LocalProductTagRepository) : ViewModel() {
    val allTags: LiveData<List<ProductTag>> = localProductTagRepository.getAllTags().asLiveData()

    fun insert(productTag: ProductTag) {
        viewModelScope.launch {
            localProductTagRepository.insert(productTag)
        }
    }

    fun update(productTag: ProductTag) {
        viewModelScope.launch {
            localProductTagRepository.update(productTag)
        }
    }

    fun delete(productTag: ProductTag) {
        viewModelScope.launch {
            localProductTagRepository.delete(productTag)
        }
    }

    fun getTagById(id: Int) {
        viewModelScope.launch {
            localProductTagRepository.getTagById(id)
        }
    }
}