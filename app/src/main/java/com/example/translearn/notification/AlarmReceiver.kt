package com.example.translearn.notification

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.translearn.R

class AlarmReceiver: BroadcastReceiver() {
    companion object {
        private var id = 1
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val builder = NotificationCompat.Builder(context!!, "channel_id")
            .setSmallIcon(R.drawable.notification_foreground)
            .setContentTitle("TransLearn alert")
            .setContentText("Time to learn!")
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(id, builder.build())
            return@with id++
        }
    }
}