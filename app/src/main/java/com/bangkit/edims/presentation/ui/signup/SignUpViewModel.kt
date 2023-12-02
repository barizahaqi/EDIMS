package com.bangkit.edims.presentation.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.edims.core.utils.Validation

class SignUpViewModel : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: MutableLiveData<String> = _message

    fun signUp(username: String, email: String, password: String, confirmPassword: String) {
        if (Validation.validateUsername(username) && Validation.validateEmail(email) && Validation.validatePassword(
                password
            ) && password == confirmPassword
        ) {
            _message.value = "Sign Up Success"
        } else {
            _message.value = "Sign Up Failed"
        }
    }
}