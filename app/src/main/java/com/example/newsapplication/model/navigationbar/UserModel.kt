package com.example.newsapplication.model.navigationbar

// Дата класс, отвечающий за хранение данных о пользователях
data class UserModel(
    val id: String,
    val email: String,
    val interests: MutableList<String>
)