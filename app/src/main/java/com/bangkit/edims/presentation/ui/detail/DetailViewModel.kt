package com.bangkit.edims.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.edims.data.Result
import com.bangkit.edims.database.Product
import com.bangkit.edims.database.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _result: MutableStateFlow<Result<Product>> = MutableStateFlow(Result.Loading)
    val result: StateFlow<Result<Product>> get() = _result

    suspend fun delete(product: Product) {
        repository.delete(product)
    }

    fun getProductById(id: Int) {
        viewModelScope.launch {
            repository.getItemsById(id)
                .catch {
                    _result.value = Result.Error(it.message.toString())
                }
                .collect {
                    _result.value = Result.Success(it)
                }
        }
    }
}