package com.bangkit.edims.presentation.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.edims.database.Product
import com.bangkit.edims.database.ProductRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repository: ProductRepository) : ViewModel() {

    fun insert(product: Product) {
        viewModelScope.launch {
            repository.insert(product)
        }
    }
}