package com.example.myproject.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.data.Result
import com.example.myproject.database.Product
import com.example.myproject.database.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _result : MutableStateFlow<Result<Product>> = MutableStateFlow(Result.Loading)
    val result : StateFlow<Result<Product>> get() = _result

    fun insert(product: Product) {
        viewModelScope.launch {
            repository.insert(product)
        }
    }
}