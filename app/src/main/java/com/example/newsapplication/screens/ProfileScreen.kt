package com.example.newsapplication.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsapplication.MainActivity
import com.example.newsapplication.R
import com.example.newsapplication.model.user.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.compose.runtime.remember as remember1

// Markup of the "Profile" screen
@Composable
fun ProfileScreen(
    auth: FirebaseAuth,
    selectedColor1: MutableState<Boolean>,
    selectedColor2: MutableState<Boolean>,
    selectedColor3: MutableState<Boolean>
) {
    val database = Firebase.database.reference
    val cUser = auth.currentUser
    val context = LocalContext.current

    val touchCounter = remember1 { mutableStateOf(0) }
    val interests = remember1 { mutableListOf<String>() }

    // Column Composable
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
            imageVector = Icons.Default.Person,
            contentDescription = "profile",
            tint = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier.size(64.dp)
        )
        // Text to Display the current Screen
        Text(text = "You are logged in as: ${cUser?.email}")
        // Add User Interests
        val user = UserModel(
            id = cUser?.uid.toString(),
            email = cUser?.email.toString(),
            interests = interests
        )
        // Interests
        Row {
            MyInterests(
                interests = interests,
                context = context,
                selectedColor1 = selectedColor1,
                selectedColor2 = selectedColor2,
                selectedColor3 = selectedColor3
            )
        }
        // Button to write the information about user
        Button(onClick = {
            database.child("Users").child(user.id).setValue(user)
        }) { Text(text = "Add") }
        // Button to logout
        Button(onClick = {
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
        }) { Text(text = "Log out") }
    }
}

@Composable
fun CheckTheInterests(
    database: DatabaseReference,
    cUser: FirebaseUser?,
    selectedColor1: MutableState<Boolean>,
    selectedColor2: MutableState<Boolean>,
    selectedColor3: MutableState<Boolean>
) {
    database.child("Users").child(cUser!!.uid).child("interests").get().addOnSuccessListener {
        when (it.value.toString()) {
            "[Interest 1]" -> selectedColor1.value = true
            "[Interest 2]" -> selectedColor2.value = true
            "[Interest 3]" -> selectedColor3.value = true
            "[Interest 1, Interest 2]" -> {
                selectedColor1.value = true
                selectedColor2.value = true
            }
            "[Interest 2, Interest 3]" -> {
                selectedColor2.value = true
                selectedColor3.value = true
            }
            "[Interest 1, Interest 3]" -> {
                selectedColor1.value = true
                selectedColor3.value = true
            }
            "[Interest 1, Interest 2, Interest 3]" -> {
                selectedColor1.value = true
                selectedColor2.value = true
                selectedColor3.value = true
            }
        }
    }
}

@Composable
fun MyInterests(
    interests: MutableList<String>,
    context: Context,
    selectedColor1: MutableState<Boolean>,
    selectedColor2: MutableState<Boolean>,
    selectedColor3: MutableState<Boolean>
) {

    val cardColor1 =
        if (selectedColor1.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val cardColor2 =
        if (selectedColor2.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val cardColor3 =
        if (selectedColor3.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                if (!selectedColor1.value)
                    interests.add(context.getString(R.string.interest1))
                else
                    interests.remove("Interest 1")
                selectedColor1.value = !selectedColor1.value
            },
        colors = CardDefaults.cardColors(
            containerColor = cardColor1
        )
    ) {
        Text(
            text = stringResource(id = R.string.interest1),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(10.dp)
        )
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                if (!selectedColor2.value)
                    interests.add(context.getString(R.string.interest2))
                else
                    interests.remove("Interest 2")
                selectedColor2.value = !selectedColor2.value
            },
        colors = CardDefaults.cardColors(
            containerColor = cardColor2
        )
    ) {
        Text(
            text = stringResource(id = R.string.interest2),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(10.dp)
        )
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                if (!selectedColor3.value)
                    interests.add(context.getString(R.string.interest3))
                else
                    interests.remove("Interest 3")
                selectedColor3.value = !selectedColor3.value
            },
        colors = CardDefaults.cardColors(
            containerColor = cardColor3
        )
    ) {
        Text(
            text = stringResource(id = R.string.interest3),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
