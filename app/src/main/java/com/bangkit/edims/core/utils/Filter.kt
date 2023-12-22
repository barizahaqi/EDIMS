package com.bangkit.edims.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import com.bangkit.edims.core.utils.DateConverter.dayToMillis

object Filter {

    fun getFilterQuery(filter: ProductFilterType): SimpleSQLiteQuery {
        val currentTime = System.currentTimeMillis()

        val almostFilter = currentTime + dayToMillis(30)
        val goodFilter = currentTime + dayToMillis(90)

        val simpleQuery = StringBuilder().append("SELECT * FROM product ")
        when (filter) {
            ProductFilterType.BAD_PRODUCTS -> {
                simpleQuery.append("WHERE dueDateMillis <= $currentTime ")
            }
            ProductFilterType.ALMOST_PRODUCTS -> {
                simpleQuery.append("WHERE dueDateMillis <= $almostFilter AND dueDateMillis > $currentTime ")
            }
            ProductFilterType.GOOD_PRODUCTS -> {
                simpleQuery.append("WHERE dueDateMillis <= $goodFilter AND dueDateMillis > $almostFilter ")
            }
            ProductFilterType.FRESH_PRODUCTS -> {
                simpleQuery.append("WHERE dueDateMillis > $goodFilter ")
            }
        }
        simpleQuery.append("ORDER BY dueDateMillis ASC")
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}