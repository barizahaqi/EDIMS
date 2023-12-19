package com.bangkit.edims.database

import com.example.myproject.utils.ProductFilterType
import kotlinx.coroutines.flow.Flow

interface ProductRepository{

    suspend fun insert(product: Product)

    suspend fun delete(product: Product)

    fun getItems(filter: ProductFilterType) : Flow<List<Product>>

    fun getItemsById(id: Int) : Flow<Product>

    fun getNearestItems() : List<Product>

    suspend fun saveNotificationSettings(status: Boolean)

    fun getNotificationSettings() : Flow<Boolean>

    suspend fun saveAutoDeleteSettings(status: Boolean)

    fun getAutoDeleteSettings() : Flow<Boolean>
}
