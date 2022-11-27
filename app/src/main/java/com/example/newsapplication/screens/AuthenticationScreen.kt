package com.example.newsapplication.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.newsapplication.MainActivity
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(auth: FirebaseAuth) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        // parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "authentication",
            tint = MaterialTheme.colorScheme.surfaceTint
        )
        // Text to Display the current Screen
        Text(text = "Authentication", color = MaterialTheme.colorScheme.onSurface)
        // OutlinedTextField to type the Email
        OutlinedTextField(
            value = email.value,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = { newText -> email.value = newText },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email address") },
            placeholder = {
                Text(
                    text = "abc@domain.com",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        // OutlinedTextField to type the password
        OutlinedTextField(
            value = password.value,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = { newText -> password.value = newText },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        // Registration button
        Button(onClick = {
            if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Toast.makeText(
                                context,
                                "User successful authorized",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        else
                            Toast.makeText(context, "User is already exist", Toast.LENGTH_SHORT)
                                .show()
                    }
            } else {
                Toast.makeText(
                    context,
                    "Please enter an email address and a password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) { Text(text = "Registered") }
        // SignIn button
        Button(onClick = {
            // Authorized user login
            if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            context.startActivity(Intent(context, MainActivity::class.java))
                        else
                            Toast.makeText(
                                context,
                                "Please check that your email address and password are correct",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
            } else {
                Toast.makeText(
                    context,
                    "Please enter an email address and a password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) { Text(text = "Sign in") }
    }
    // Запрет возврата к экрану Аутентификации
    //BackHandler(enabled = true) {}
}