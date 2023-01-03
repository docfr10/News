package com.example.newsapplication.model.user

// Data class responsible for storing user data
data class UserModel(
    val id: String,
    val email: String,
    val interests: MutableList<String>
)