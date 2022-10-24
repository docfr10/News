package com.example.newsapplication.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.newsapplication.MainActivity
import com.example.newsapplication.model.UserModel
import com.example.newsapplication.ui.theme.Purple500
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//Разметка экрана профиля
@Composable
fun ProfileScreen(auth: FirebaseAuth) {
    val database = Firebase.database.reference
    val cUser = auth.currentUser
    val context = LocalContext.current

    val touchCounter = remember { mutableStateOf(0) }
    val interests = remember { mutableStateOf("") }

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
            modifier = Modifier
                .clickable {
                    when (touchCounter.value) {
                        0 -> {
                            Toast
                                .makeText(context, "Click again to log out", Toast.LENGTH_SHORT)
                                .show()
                            touchCounter.value++
                        }
                        1 -> {
                            auth.signOut()
                            context.startActivity(Intent(context, MainActivity::class.java))
                        }
                    }
                }
                .size(64.dp)
        )
        // Text to Display the current Screen
        Text(text = "You are logged in as: ${cUser?.email}")
        // Interests
        OutlinedTextField(
            value = interests.value,
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            onValueChange = { newText -> interests.value = newText },
            label = { Text(text = "Interests") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        
        val user = UserModel(
            id = cUser?.uid.toString(),
            email = cUser?.email.toString(),
            interests = interests.value
        )
        // Button to write the information about user
        Button(onClick = {
            database.child("Users").child(user.id).setValue(user)
        }) { Text(text = "Add") }
    }
}

