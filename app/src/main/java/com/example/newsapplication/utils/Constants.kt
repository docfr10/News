package com.example.newsapplication.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.newsapplication.model.navigationbar.BottomNavItemModel

// An object containing information about all NavigationBar icons
object Constants {
    val BottomNavItems = listOf(
        BottomNavItemModel(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),
        BottomNavItemModel(
            label = "Projects",
            icon = Icons.Filled.Create,
            route = "projects"
        ),
        BottomNavItemModel(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "profile"
        ),
        BottomNavItemModel(
            label = "Settings",
            icon = Icons.Filled.Settings,
            route = "settings"
        ),
        BottomNavItemModel(
            label = "About",
            icon = Icons.Filled.Info,
            route = "about"
        ),
    )
}