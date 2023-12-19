package com.bangkit.edims.database

import com.bangkit.edims.core.utils.Filter
import com.bangkit.edims.core.utils.ProductFilterType
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    override suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    override suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    override fun getItems(filter: ProductFilterType): Flow<List<Product>> {
        val filterQuery = Filter.getFilterQuery(filter)
        return productDao.getItems(filterQuery)
    }

    override fun getItemsById(id: Int): Flow<Product> {
        return productDao.getItemsById(id)
    }
}
