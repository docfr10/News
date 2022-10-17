package com.example.newsapplication.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import com.example.newsapplication.models.BottomNavItem

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