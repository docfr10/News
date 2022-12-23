package com.example.newsapplication.viewmodel

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.newsapplication.channelID
import com.example.newsapplication.messageExtra
import com.example.newsapplication.model.notifications.NotificationsModel
import com.example.newsapplication.notificationID
import com.example.newsapplication.titleExtra

class HomeViewModel : ViewModel() {
    fun createNotifications(activity: Activity, context: Context) {
        val title = "Some title" //Название уведомления
        val message = "Some message" //Текст уведомления
        val intent = Intent(context, NotificationsModel::class.java)
        //Записываем дату когда необходимо отправить уведомление
        val alarmManager =
            activity.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        //Передаем название и текст уведомления в Notifications
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        //Создаем широковещательный сигнал для отправки уведомления
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                notificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 5000,
            pendingIntent
        )
    }

    fun createNotificationChannel(activity: Activity) {
        val notificationManager =
            activity.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_DEFAULT
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(channelID, name, importance)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        channel.description = desc
        notificationManager.createNotificationChannel(channel)
    }
}