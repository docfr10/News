package com.example.newsapplication.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.newsapplication.model.BottomNavItem

// Объект, содержащий информацию о всех иконках BottomBar
object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "profile"
        ),
        BottomNavItem(
            label = "Settings",
            icon = Icons.Filled.Settings,
            route = "settings"
        ),
        BottomNavItem(
            label = "About",
            icon = Icons.Filled.Info,
            route = "about"
        ),
    )
}