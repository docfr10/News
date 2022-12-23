package com.example.newsapplication.model.navigationbar

import androidx.compose.ui.graphics.vector.ImageVector

// Data class that stores data about the lower elements of the navigation screen
data class BottomNavItemModel(
    val label: String,
    val icon: ImageVector,
    val route: String,
)