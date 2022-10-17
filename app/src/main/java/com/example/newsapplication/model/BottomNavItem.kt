package com.example.newsapplication.model

import androidx.compose.ui.graphics.vector.ImageVector

//Дата класс, который хранит данные о нижних элементах экрана навигации
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)