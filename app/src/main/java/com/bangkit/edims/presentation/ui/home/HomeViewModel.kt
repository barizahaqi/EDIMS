package com.bangkit.edims.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.bangkit.edims.database.Product
import com.bangkit.edims.database.ProductRepository
import com.bangkit.edims.core.utils.ProductFilterType
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repository: ProductRepository) : ViewModel() {

    fun getFilteredProducts(filter: ProductFilterType): Flow<List<Product>> {
        return repository.getItems(filter)
    }
}