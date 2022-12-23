package com.example.newsapplication.screens

import android.app.*
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.newsapplication.viewmodel.HomeViewModel


//Разметка домашнего экрана
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(activity: Activity, context: Context, homeViewModel: HomeViewModel) {
    val notificationText = remember { mutableStateOf("") }

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
        // OutlinedTextField to type the new notification
        OutlinedTextField(
            value = notificationText.value,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = { newText -> notificationText.value = newText },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Type a notification text") },
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        // Button, to send notification
        Button(onClick = {
            homeViewModel.createNotificationChannel(activity = activity)
            homeViewModel.createNotifications(
                activity = activity,
                context = context,
                notificationText = notificationText
            )
        }) {
            Text(text = "Send notification")
        }
    }
}


