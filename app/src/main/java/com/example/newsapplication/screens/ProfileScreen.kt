package com.example.newsapplication.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.newsapplication.MainActivity
import com.example.newsapplication.ui.theme.Purple500
import com.google.firebase.auth.FirebaseAuth

//Разметка экрана профиля
@Composable
fun ProfileScreen(auth: FirebaseAuth) {
    val cUser = auth.currentUser
    val context = LocalContext.current

    val touchCounter = remember { mutableStateOf(0) }

    // Column Composable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        // parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "profile",
            tint = Purple500,
            modifier = Modifier.clickable {
                when (touchCounter.value) {
                    0 -> {
                        Toast.makeText(context, "Click again to log out", Toast.LENGTH_SHORT)
                            .show()
                        touchCounter.value++
                    }
                    1 -> {
                        auth.signOut()
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }
                }
            }
        )
        // Text to Display the current Screen
        Text(text = "You are logged in as: ${cUser?.email}")
    }
}
