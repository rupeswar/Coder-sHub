package com.rupeswar.codershub.firebaseMessaging

import android.app.Notification
import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rupeswar.codershub.utils.sendNotification

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("myApp", "Refreshed Token $token")

        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        TODO("Not yet implemented")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("myApp", "From: ${remoteMessage.from}")

        Log.d("myApp", "Message data payload: ${remoteMessage.data}")

        remoteMessage.notification?.let {
            Log.d("myApp", "Message Notification Body: ${it.body}")
            sendNotification(it.body as String)
        }
    }

    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }
}