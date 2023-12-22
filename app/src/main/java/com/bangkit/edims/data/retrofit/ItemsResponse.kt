package com.bangkit.edims.data.retrofit

import com.google.gson.annotations.SerializedName

data class ItemsResponse (
    @field:SerializedName("message")
    val message : String,

    @field:SerializedName("items")
    val items : List<Items>
)

data class Items(
    @field:SerializedName("userId")
    val userId: Int,

    @field:SerializedName("id")
    val id : Int,

    @field:SerializedName("name")
    val name : String,

    @field:SerializedName("image")
    val image : String,

    @field:SerializedName("category")
    val category : String,

    @field:SerializedName("dueDateMillis")
    val dueDataMillis : String,
)