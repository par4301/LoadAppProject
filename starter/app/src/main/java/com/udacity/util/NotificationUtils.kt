package com.udacity.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity
import com.udacity.R

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context, status: String) {
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    contentIntent.putExtra("fileName", messageBody)
    contentIntent.putExtra("status", status)

    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationBuilder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.file_download_complete_channel_id)
    )
            .setSmallIcon(R.drawable.ic_assistant)
            .setContentTitle(applicationContext.getString(R.string.download_complete))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(NOTIFICATION_ID, notificationBuilder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
