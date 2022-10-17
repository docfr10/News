package com.example.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapplication.screens.AboutScreen
import com.example.newsapplication.screens.HomeScreen
import com.example.newsapplication.screens.ProfileScreen
import com.example.newsapplication.screens.SettingsScreen
import com.example.newsapplication.ui.theme.NewsApplicationTheme
import com.example.newsapplication.ui.theme.Purple700
import com.example.newsapplication.utils.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                // remember navController so it does not
                // get recreated on recomposition
                val navController = rememberNavController()
                Surface(color = Color.White) {
                    // Scaffold Component
                    Scaffold(
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            NavHostContainer(navController = navController, padding = padding)
                        }
                    )
                }
            }
        }
    }
}

//Навигация по экранам
@Composable
private fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        // set the start destination as home
        startDestination = "home",
        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            // route : Home
            composable("home") {
                HomeScreen()
            }
            // route : profile
            composable("profile") {
                ProfileScreen()
            }
            // route : about
            composable("about") {
                AboutScreen()
            }
            //route : settings
            composable("settings") {
                SettingsScreen()
            }
        })
}

//Вывод всех иконок экранов
@Composable
private fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        // set background color
        backgroundColor = Purple700
    ) {
        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route
        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->
            // Place the bottom nav items
            BottomNavigationItem(
                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },
                // Icon of navItem
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                },
                // label
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false
            )
        }
    }
}