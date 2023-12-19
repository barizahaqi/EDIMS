package com.bangkit.edims.database

import com.bangkit.edims.core.utils.ProductFilterType
import kotlinx.coroutines.flow.Flow

interface ProductRepository{

    suspend fun insert(product: Product)

    suspend fun delete(product: Product)

    fun getItems(filter: ProductFilterType) : Flow<List<Product>>

    fun getItemsById(id: Int) : Flow<Product>
}
