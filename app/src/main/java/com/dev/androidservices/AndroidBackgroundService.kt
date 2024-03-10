package com.dev.androidservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class AndroidBackgroundService : Service() {
    private val TAG = "BACKGROUND_SERVICE"
    private var count = -1
    override fun onCreate() {
        Log.d(TAG, "onCreate: called")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    Log.d(TAG, "service is running - $count")
                    count += 1
                }
            }, 0, 1000)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }
}