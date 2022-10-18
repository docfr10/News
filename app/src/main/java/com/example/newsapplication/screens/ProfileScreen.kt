package com.example.newsapplication.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.newsapplication.MainActivity
import com.example.newsapplication.ui.theme.Purple500
import com.google.firebase.auth.FirebaseAuth

//Разметка экрана профиля
@Composable
fun ProfileScreen(auth: FirebaseAuth) {
    val cUser = auth.currentUser
    
    val context = LocalContext.current

    // Column Composable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        // parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "profile",
            tint = Purple500
        )
        // Text to Display the current Screen
        Text(text = "Profile", color = Color.Black)
        Text(text = "You are logged in as: ${cUser?.email}")
        Button(onClick = {
            auth.signOut()
            context.startActivity(Intent(context, MainActivity::class.java))
        }) { Text(text = "Sign out") }
    }
}
