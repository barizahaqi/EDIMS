package com.bangkit.edims.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.edims.data.Result
import com.bangkit.edims.data.User
import com.bangkit.edims.data.retrofit.LoginResponse
import com.bangkit.edims.database.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _result: MutableStateFlow<Result<LoginResponse>> = MutableStateFlow(Result.Loading)
    val result: MutableStateFlow<Result<LoginResponse>> get() = _result

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
                .collect {
                    _result.value = it
                }
        }
    }

    fun saveLoginData(userId: Int, username: String, email: String, token: String) {
        val user = User(
            userId = userId,
            username = username,
            email = email,
            token = token,
            imageProfile = null
        )
        viewModelScope.launch {
            repository.saveLoginData(user)
        }
    }

    fun resetResult() {
        _result.value = Result.Loading
    }
}