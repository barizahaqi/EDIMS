package com.bangkit.edims.data.retrofit

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: UserData,

    @field:SerializedName("token")
    val token: String
)

data class UserData(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String,
)