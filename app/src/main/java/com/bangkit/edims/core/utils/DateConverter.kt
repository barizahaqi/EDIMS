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

    private fun remainingTime(timeMillis: Long): Long {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis

        return calendar.timeInMillis - currentTime
    }

    fun remainingHours(timeMillis: Long): Long {
        val diffMillis = remainingTime(timeMillis)

        return diffMillis / (60 * 60 * 1000)
    }

    fun remainingDays(timeMillis: Long): Long {
        val diffMillis = remainingTime(timeMillis)

        return diffMillis / (24 * 60 * 60 * 1000)
    }

    fun dayToMillis(days: Long): Long {
        val millisInDay = 24 * 60 * 60 * 1000
        return days * millisInDay
    }
}