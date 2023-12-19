package com.bangkit.edims.database

import com.bangkit.edims.core.utils.Filter
import com.bangkit.edims.core.utils.ProductFilterType
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val preference: SettingPreference,
    ) : ProductRepository {
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

    override fun getNearestItems(): List<Product> {
        val days : Long = 3
        val dueDateMillis : Long = (days * 24 * 60 * 60 * 1000)
        return productDao.getNearestItems(dueDateMillis)
    }

    override suspend fun saveNotificationSettings(status: Boolean) {
        preference.saveNotificationSettings(status)
    }

    override fun getNotificationSettings(): Flow<Boolean> {
        return preference.getNotificationSettings
    }

    override suspend fun saveAutoDeleteSettings(status: Boolean) {
        preference.saveAutoDeleteSettings(status)
    }

    override fun getAutoDeleteSettings(): Flow<Boolean> {
        return preference.getAutoDeleteSettings
    }
}
