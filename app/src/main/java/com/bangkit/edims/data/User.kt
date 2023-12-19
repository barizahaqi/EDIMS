package com.bangkit.edims.data

import com.bangkit.edims.database.Product

data class User(
    val item: List<Product>?,
    val username: String?,
    val profileIcon: String?,
    val email: String?,
)
