package com.example.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapplication.model.BottomNavItemModel
import com.example.newsapplication.screens.*
import com.example.newsapplication.ui.theme.NewsApplicationTheme
import com.example.newsapplication.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val cUser = auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                // remember navController so it does not
                // get recreated on recomposition
                val navController = rememberNavController()

                if (cUser != null)
                // Launch the app screen
                    AppScreen(navController = navController, auth = auth)
                else
                // Launch the authentication screen
                    Authentication(navController = navController, auth = auth)
            }
        }
    }
}

// Переход на экран Аутентификации
@Composable
private fun Authentication(
    navController: NavHostController,
    auth: FirebaseAuth
) {
    BottomNavItemModel(
        label = "Authentication",
        icon = Icons.Filled.AccountCircle,
        route = "authentication"
    )
    NavHost(navController = navController, startDestination = "authentication", builder = {
        composable("authentication") { AuthenticationScreen(auth = auth) }
    })
}

@Composable
fun AppScreen(navController: NavHostController, auth: FirebaseAuth) {
    val database = Firebase.database.reference
    val cUser = auth.currentUser

    val selectedColor1 = remember { mutableStateOf(false) }
    val selectedColor2 = remember { mutableStateOf(false) }
    val selectedColor3 = remember { mutableStateOf(false) }

    CheckTheInterests(
        database = database,
        cUser = cUser,
        selectedColor1,
        selectedColor2,
        selectedColor3
    )

    Surface(color = MaterialTheme.colors.surface) {
        // Scaffold Component
        Scaffold(
            // Bottom navigation
            bottomBar = { BottomNavigationBar(navController = navController) },
            content = { padding ->
                NavHostContainer(
                    navController = navController,
                    padding = padding,
                    auth = auth,
                    selectedColor1 = selectedColor1,
                    selectedColor2 = selectedColor2,
                    selectedColor3 = selectedColor3
                )
            }
        )
    }

    // Запрет возврата к экрану Аутентификации
    BackHandler(enabled = true) {}
}

// Навигация по экранам
@Composable
private fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    auth: FirebaseAuth,
    selectedColor1: MutableState<Boolean>,
    selectedColor2: MutableState<Boolean>,
    selectedColor3: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        // set the start destination as home
        startDestination = "home",
        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            // route : Home
            composable("home") { HomeScreen() }
            // route : profile
            composable("profile") {
                ProfileScreen(
                    auth = auth,
                    selectedColor1 = selectedColor1,
                    selectedColor2 = selectedColor2,
                    selectedColor3 = selectedColor3
                )
            }
            // route : about
            composable("about") { AboutScreen() }
            //route : settings
            composable("settings") { SettingsScreen() }
        })
}

// Вывод всех иконок экранов
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        // set background color
        backgroundColor = MaterialTheme.colors.background
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
                onClick = { navController.navigate(navItem.route) },
                // Icon of navItem
                icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.label) },
                // label
                label = { Text(text = navItem.label) },
                alwaysShowLabel = false
            )
        }
    }
}