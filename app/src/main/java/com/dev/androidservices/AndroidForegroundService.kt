package com.dev.androidservices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class AndroidForegroundService : Service() {
    private val TAG = "FOREGROUND_SERVICE"
    private var count = -1
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    Log.d(TAG, "ForegroundService: $count")
                    count += 1
                }
            }, 0, 1000)
        }
        startForeground(101, createNotification(this))
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(context: Context): Notification {
        val channelId = "your_channel_id"
        val channelName = "Your Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH

        // Create a NotificationChannel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance)
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // Build the notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("My Notification")
            .setContentText("This is a notification for a foreground service.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        return builder.build()
    }
}