package com.bangkit.edims.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.edims.data.Result
import com.bangkit.edims.data.User
import com.bangkit.edims.database.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: ProductRepository) : ViewModel() {
    private val _result: MutableStateFlow<Result<User>> = MutableStateFlow(Result.Loading)
    val result: StateFlow<Result<User>> get() = _result

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

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}