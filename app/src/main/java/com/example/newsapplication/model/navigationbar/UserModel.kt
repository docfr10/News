package com.example.newsapplication.model.navigationbar

// Data class responsible for storing user data
data class UserModel(
    val id: String,
    val email: String,
    val interests: MutableList<String>
)