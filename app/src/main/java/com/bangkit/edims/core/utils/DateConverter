package com.bangkit.edims.core.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

object DateConverter {
    fun convertMillisToString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun remainingTime(timeMillis: Long): Long {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis

        val diffMillis = calendar.timeInMillis - currentTime

        return diffMillis / (24 * 60 * 60 * 1000)
    }
}
