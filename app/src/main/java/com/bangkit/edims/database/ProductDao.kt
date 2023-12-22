package com.bangkit.edims.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()

    @RawQuery(observedEntities = [Product::class])
    fun getItems(query: SupportSQLiteQuery): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE id=:productId")
    fun getItemsById(productId: Int): Flow<Product>

    @Query("SELECT * FROM product WHERE dueDateMillis <= :dueDateMillis AND dueDateMillis > :currentTime ORDER BY dueDateMillis ASC")
    fun getNearestItems(dueDateMillis : Long, currentTime : Long) : List<Product>
}