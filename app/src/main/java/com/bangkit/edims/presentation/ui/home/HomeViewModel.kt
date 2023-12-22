package com.bangkit.edims.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.edims.core.utils.ProductFilterType
import com.bangkit.edims.data.Result
import com.bangkit.edims.data.User
import com.bangkit.edims.data.retrofit.ItemsResponse
import com.bangkit.edims.database.Product
import com.bangkit.edims.database.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _result: MutableStateFlow<Result<User>> = MutableStateFlow(Result.Loading)
    val result: StateFlow<Result<User>> get() = _result

    private val _resultApi: MutableStateFlow<Result<ItemsResponse>> =
        MutableStateFlow(Result.Loading)
    val resultApi: StateFlow<Result<ItemsResponse>> get() = _resultApi

    val isDataFetched = repository.isDataFetched()

    fun getFilteredProducts(filter: ProductFilterType): Flow<List<Product>> {
        return repository.getItems(filter)
    }

    fun getUserData() {
        viewModelScope.launch {
            repository.getUserData()
                .catch {
                    _result.value = Result.Error(it.message.toString())
                }
                .collect {
                    _result.value = Result.Success(it)
                }
        }
    }

    fun getAllData() {
        viewModelScope.launch {
            if (!repository.isDataFetched()) {
                repository.getAllData()
                    .collect {
                        _resultApi.value = it
                    }
            }
        }
    }

    fun addData(listProduct: List<Product>) {
        viewModelScope.launch {
            repository.insertAll(listProduct)
        }
    }

    fun resetResultApi() {
        _resultApi.value = Result.Loading
    }
}