package com.example.newsapplication.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsapplication.MainActivity
import com.example.newsapplication.R
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
    val interests = remember { mutableListOf<String>() }

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
        MyInterests(interests = interests, context = context)
        // Add User Interests
        val user = UserModel(
            id = cUser?.uid.toString(),
            email = cUser?.email.toString(),
            interests = interests
        )
        // Button to write the information about user
        Button(onClick = {
            database.child("Users").child(user.id).setValue(user)
        }) { Text(text = "Add") }
    }
}

@Composable
fun MyInterests(interests: MutableList<String>, context: Context) {

    val selectedColor1 = remember { mutableStateOf(false) }
    val selectedColor2 = remember { mutableStateOf(false) }
    val selectedColor3 = remember { mutableStateOf(false) }

    val cardColor1 = if (selectedColor1.value) Color.Gray else Color.White
    val cardColor2 = if (selectedColor2.value) Color.Gray else Color.White
    val cardColor3 = if (selectedColor3.value) Color.Gray else Color.White

    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                if (!selectedColor1.value)
                    interests.add(context.getString(R.string.interest1))
                else
                    interests.remove("Interest 1")
                selectedColor1.value = !selectedColor1.value
            },
        backgroundColor = cardColor1
    ) {
        Text(
            text = stringResource(id = R.string.interest1),
            modifier = Modifier
                .padding(10.dp)
        )
    }

    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                if (!selectedColor2.value)
                    interests.add(context.getString(R.string.interest2))
                else
                    interests.remove("Interest 2")
                selectedColor2.value = !selectedColor2.value
            },
        backgroundColor = cardColor2
    ) {
        Text(
            text = stringResource(id = R.string.interest2),
            modifier = Modifier
                .padding(10.dp)
        )
    }

    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                if (!selectedColor3.value)
                    interests.add(context.getString(R.string.interest3))
                else
                    interests.remove("Interest 3")
                selectedColor3.value = !selectedColor3.value
            },
        backgroundColor = cardColor3
    ) {
        Text(
            text = stringResource(id = R.string.interest3),
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
