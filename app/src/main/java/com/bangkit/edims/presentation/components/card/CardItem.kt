package com.bangkit.edims.presentation.components.card

import androidx.compose.ui.graphics.Color
import com.bangkit.edims.database.Product

data class CardItem(
    val id: Int,
    val title: String,
    val icon: Int,
    val color: Color,
    val listItem: List<Product>
)
