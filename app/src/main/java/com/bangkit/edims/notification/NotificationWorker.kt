package com.bangkit.edims.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.bangkit.edims.core.utils.ID_REPEATING

class NotificationWorker : BroadcastReceiver() {

    private val notificationHelper: NotificationHelper by lazy { NotificationHelper() }

    override fun onReceive(context: Context, intent: Intent) {
        notificationHelper.onReceive(context)
    }

    fun setDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationWorker::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_REPEATING,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        alarmManager.setRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, NotificationWorker::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, ID_REPEATING, intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
    }
}