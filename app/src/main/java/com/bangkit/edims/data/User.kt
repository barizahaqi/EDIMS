package com.example.myproject.data

import com.example.myproject.database.Product

data class User(
    val item: List<Product>?,
    val username: String?,
    val profileIcon: String?,
    val email: String?,
)
