package com.example.newsapplication.screens

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.newsapplication.*
import com.example.newsapplication.model.NotificationsModel


//Разметка домашнего экрана
@Composable
fun HomeScreen(activity: Activity, context: Context) {
    // Column Composable,
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        // Parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "home",
            tint = MaterialTheme.colorScheme.surfaceTint
        )
        // Text to Display the current Screen
        Text(text = "Home")

        // Button, to send notification
        Button(onClick = {
            createNotificationChannel(activity)
            createNotifications(activity, context)
        }) {
            Text(text = "Send notification")
        }
    }
}

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

    //val newTime = System.currentTimeMillis() + 5000

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        System.currentTimeMillis(),
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
