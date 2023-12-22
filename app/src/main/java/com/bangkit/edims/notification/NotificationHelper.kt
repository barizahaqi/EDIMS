package com.bangkit.edims.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bangkit.edims.R
import com.bangkit.edims.core.utils.DateConverter
import com.bangkit.edims.core.utils.NOTIFICATION_CHANNEL_ID
import com.bangkit.edims.core.utils.NOTIFICATION_CHANNEL_NAME
import com.bangkit.edims.core.utils.NOTIFICATION_ID
import com.bangkit.edims.core.utils.executeThread
import com.bangkit.edims.database.Product
import com.bangkit.edims.database.ProductRepository
import com.bangkit.edims.presentation.main.MainActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationHelper : KoinComponent {
    private val repository : ProductRepository by inject()

    fun onReceive(context: Context) {
        executeThread {
            val listProduct = repository.getNearestItems()
            showNotification(context, listProduct)
        }
    }

    private fun showNotification(context: Context, content: List<Product>) {
        val notificationStyle = NotificationCompat.InboxStyle()
        content.forEach {
            val date = DateConverter.convertMillisToString(it.dueDateMillis)
            val message = ("$date - ${it.name}")
            notificationStyle.addLine(message)
        }

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Almost Expired Product")
            .setContentIntent(pendingIntent)
            .setStyle(notificationStyle)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Product Reminder"
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300)
            }
            notification.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
}