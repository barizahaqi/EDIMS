package com.bangkit.edims.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("image")
    val image: String,

    @ColumnInfo("category")
    val category: String,

    @ColumnInfo("dueDateMillis")
    val dueDateMillis: Long,
)