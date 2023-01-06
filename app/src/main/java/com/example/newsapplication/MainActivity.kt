package com.example.newsapplication

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.material3.BottomAppBarDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapplication.model.navigationbar.BottomNavItemModel
import com.example.newsapplication.screens.*
import com.example.newsapplication.ui.theme.NewsApplicationTheme
import com.example.newsapplication.utils.Constants
import com.example.newsapplication.viewmodel.AuthenticationViewModel
import com.example.newsapplication.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    // Objects for working with Firebase
    private val auth = FirebaseAuth.getInstance()
    private val cUser = auth.currentUser

    // ViewModel objects
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var authenticationViewModel: AuthenticationViewModel

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                val provider = ViewModelProvider(this)
                homeViewModel = provider[HomeViewModel::class.java]
                authenticationViewModel = provider[AuthenticationViewModel::class.java]

                // Remember navController so it does not
                // Get recreated on recomposition
                val navController = rememberNavController()

                val activity = LocalContext.current as Activity
                val context = LocalContext.current

                if (cUser != null)
                // Launch the app screen
                    AppScreen(
                        activity = activity,
                        context = context,
                        navController = navController,
                        auth = auth,
                        homeViewModel = homeViewModel,
                    )
                else
                // Launch the authentication screen
                    Authentication(
                        context = context,
                        window = window,
                        navController = navController,
                        authenticationViewModel = authenticationViewModel,
                        auth = auth
                    )
            }
        }
    }
}

// Switching to the Authentication screen
@RequiresApi(Build.VERSION_CODES.R)
@Composable
private fun Authentication(
    navController: NavHostController,
    auth: FirebaseAuth,
    authenticationViewModel: AuthenticationViewModel,
    window: Window,
    context: Context
) {
    BottomNavItemModel(
        label = "Authentication",
        icon = Icons.Filled.AccountCircle,
        route = "authentication"
    )
    NavHost(navController = navController, startDestination = "authentication", builder = {
        composable("authentication") {
            AuthenticationScreen(
                context = context,
                window = window,
                authenticationViewModel = authenticationViewModel,
                auth = auth
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    navController: NavHostController,
    auth: FirebaseAuth,
    homeViewModel: HomeViewModel,
    activity: Activity,
    context: Context
) {
    // Hide bottom bar
    val isShowBottomBar = remember { mutableStateOf(false) }

    Surface(color = MaterialTheme.colorScheme.surface) {
        // Scaffold Component
        Scaffold(
            // Bottom navigation
            bottomBar = { if (isShowBottomBar.value) BottomNavigationBar(navController = navController) },
            content = { padding ->
                NavHostContainer(
                    activity = activity,
                    context = context,
                    navController = navController,
                    padding = padding,
                    auth = auth,
                    homeViewModel = homeViewModel,
                    isShowBottomBar = isShowBottomBar
                )
            }
        )
    }

    // TODO - Fix
    // Запрет возврата к экрану Аутентификации
    //BackHandler(enabled = true) {}
}

// Screen Navigation
@Composable
private fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    auth: FirebaseAuth,
    isShowBottomBar: MutableState<Boolean>,
    homeViewModel: HomeViewModel,
    activity: Activity,
    context: Context
) {
    NavHost(
        navController = navController,
        // Set the start destination as splash screen
        startDestination = "splashScreen",
        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            // route : Splash screen
            composable("splashScreen") {
                AnimatedSplashScreen(navController = navController)
            }
            // route : Home
            composable("home") {
                HomeScreen(
                    activity = activity,
                    context = context,
                    homeViewModel = homeViewModel,
                    isShowBottomBar = isShowBottomBar
                )
            }
            // route : Profile
            composable("profile") { ProfileScreen(auth = auth) }
            // route : About
            composable("about") { AboutScreen() }
            // route : Settings
            composable("settings") { SettingsScreen() }
        })
}

// Output of all screen icons
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        // Set background color
        containerColor = NavigationBarDefaults.containerColor,
        contentColor = MaterialTheme.colorScheme.contentColorFor(containerColor),
        tonalElevation = NavigationBarDefaults.Elevation,
    ) {
        // Observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        // Observe current route to change the icon
        // Color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route
        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->
            // Place the bottom nav items
            NavigationBarItem(
                // It currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                // Navigate on click
                onClick = { navController.navigate(navItem.route) },
                // Icon of navItem
                icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.label) },
                // Label
                label = { Text(text = navItem.label) },
                alwaysShowLabel = false
            )
        }
    }
}