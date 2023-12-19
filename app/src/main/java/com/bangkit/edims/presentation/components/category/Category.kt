package com.bangkit.edims.presentation.components.category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val name: String,
    val icon: ImageVector
)

object CategoryItem {
    val items = listOf(
        Category(
            name = "Food and beverages",
            icon = Icons.Default.Fastfood
        ),
        Category(
            name = "Medicines",
            icon = Icons.Default.MedicalInformation
        ),
        Category(
            name = "Other",
            icon = Icons.Default.Folder
        ),
    )
}


