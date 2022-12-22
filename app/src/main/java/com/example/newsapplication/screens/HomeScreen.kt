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
import com.example.newsapplication.viewmodel.HomeViewModel


//Разметка домашнего экрана
@Composable
fun HomeScreen(activity: Activity, context: Context, homeViewModel: HomeViewModel) {
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
            homeViewModel.createNotificationChannel(activity = activity)
            homeViewModel.createNotifications(activity = activity, context = context)
        }) {
            Text(text = "Send notification")
        }
    }
}


