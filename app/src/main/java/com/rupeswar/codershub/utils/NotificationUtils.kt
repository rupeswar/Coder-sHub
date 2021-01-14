package com.rupeswar.codershub.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.rupeswar.codershub.MainActivity
import com.rupeswar.codershub.R

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
//    Implementing onTap of notification
//    val contentIntent = Intent(applicationContext, MainActivity::class.java)
//    val contentPendingIntent = PendingIntent.getActivity(applicationContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    val contentPendingIntent =
        NavDeepLinkBuilder(applicationContext).setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_codeforces_contests)
            .createPendingIntent()
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.default_notification_channel_id)
    ).setContentText(messageBody).setSmallIcon(R.drawable.ic_notification_foreground)
        .setContentIntent(contentPendingIntent).setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())

//    To send a notification, write like this
//    val notificationManager = ContextCompat.getSystemService(
//        app,
//        NotificationManager::class.java
//    ) as NotificationManager
//    notificationManager.sendNotification(app.getString(R.string.timer_running), app)
}

fun NotificationManager.cancelNotifications() {
    cancelAll()

//    To clear all previous notifications, write like this
//    val notificationManager =
//        ContextCompat.getSystemService(
//            app,
//            NotificationManager::class.java
//        ) as NotificationManager
//    notificationManager.cancelNotifications()
}