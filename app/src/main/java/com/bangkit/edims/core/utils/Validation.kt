package com.bangkit.edims.core.utils

class Validation {
    companion object {
        fun validateEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun validatePassword(password: String): Boolean {
            return password.length >= 8
        }

        fun validateUsername(username: String): Boolean {
            return username.length >= 6
        }

        fun validateEmpty(text: String) : Boolean {
            return text.isNotEmpty()
        }
    }
}