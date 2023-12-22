package com.bangkit.edims.presentation.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.edims.data.Result
import com.bangkit.edims.data.retrofit.UploadResponse
import com.bangkit.edims.database.Product
import com.bangkit.edims.database.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _resultApi: MutableStateFlow<Result<UploadResponse>> =
        MutableStateFlow(Result.Loading)
    val resultApi: StateFlow<Result<UploadResponse>> get() = _resultApi

    fun insert(product : Product) {
        viewModelScope.launch {
            repository.insert(product)
        }
    }

    fun uploadProduct(name: RequestBody, imageFile: MultipartBody.Part, category: RequestBody, date: RequestBody) {
        viewModelScope.launch {
            repository.uploadProductApi(name, imageFile, category, date)
                .collect{
                    _resultApi.value = it
                }
        }
    }

    fun resetResult() {
        _resultApi.value = Result.Loading
    }
}