package com.example.a46

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BackTimer : Service() {
    private val CHANNEL_ID = "timer_channel"
    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    private fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID, "Timer Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val seconds:Long = intent.getLongExtra("sec", 0)
        CoroutineScope(Dispatchers.Default).launch {
            delay(seconds * 1000L)
            createAndShowNotification()
            stopSelf()
        }
        return START_NOT_STICKY
    }

    private fun createAndShowNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Таймер завершён")
            .setContentText("Время вышло!")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        manager.notify(1, notification)
    }

    override fun onBind(intent: Intent): IBinder? = null
}