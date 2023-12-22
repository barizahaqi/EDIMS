package com.bangkit.edims.presentation.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.edims.data.Result
import com.bangkit.edims.data.retrofit.SignupResponse
import com.bangkit.edims.database.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _result: MutableStateFlow<Result<SignupResponse>> = MutableStateFlow(Result.Loading)
    val result: MutableStateFlow<Result<SignupResponse>> get() = _result

    fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {
            repository.signup(email, username, password)
                .collect {
                    _result.value = it
                }
        }
    }

    fun resetResult() {
        _result.value = Result.Loading
    }
}