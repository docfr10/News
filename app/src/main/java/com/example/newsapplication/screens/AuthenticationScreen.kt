package com.example.newsapplication.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.newsapplication.MainActivity
import com.example.newsapplication.ui.theme.Purple500
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthenticationScreen(auth: FirebaseAuth) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val context = LocalContext.current

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
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "authentication",
            tint = Purple500
        )
        // Text to Display the current Screen
        Text(text = "authentication", color = Color.Black)
        // OutlinedTextField to type the Email
        OutlinedTextField(
            value = email.value,
            onValueChange = { newText -> email.value = newText },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Email address", color = Color.Black
                )
            },
            placeholder = { Text(text = "abc@domain.com") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        // OutlinedTextField to type the password
        OutlinedTextField(
            value = password.value,
            onValueChange = { newText -> password.value = newText },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Password", color = Color.Black
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        // Registration button
        Button(onClick = {
            auth.createUserWithEmailAndPassword(
                email.value,
                password.value
            )
        }) { Text(text = "Registered") }
        // SignIn button
        Button(onClick = {
            // Authorized user login
            auth.signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        context.startActivity(Intent(context, MainActivity::class.java))
                }
        }) { Text(text = "Sign in") }
    }
}