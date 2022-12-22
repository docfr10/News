package com.example.newsapplication

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

//Класс, отвечающий за создание уведомлений
class Notifications : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val intent1 = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        Log.d("FFFFFFFFFFFFFFFF", "FFFFFFFFFFFFFFFFFff")

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}