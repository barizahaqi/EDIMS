package com.bangkit.edims.presentation.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.edims.core.utils.Validation

class LoginViewModel : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: MutableLiveData<String> = _message

    fun login(email: String, password: String) {
        if (Validation.validateEmail(email) && Validation.validatePassword(
                password
            )
        ) {
            _message.value = "Login Success"
        } else {
            _message.value = "Login Failed"
        }
    }
}