package com.bangkit.edims.data.retrofit

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("item")
	val item: Item? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Item(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("userid")
	val userid: Int? = null
)
