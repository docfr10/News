package com.example.newsapplication

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    // Objects for working with Firebase
    private val auth = FirebaseAuth.getInstance()
    private val cUser = auth.currentUser

    // ViewModel objects
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                val provider = ViewModelProvider(this)
                homeViewModel = provider[HomeViewModel::class.java]
                authenticationViewModel = provider[AuthenticationViewModel::class.java]

                // remember navController so it does not
                // get recreated on recomposition
                val navController = rememberNavController()

                if (cUser != null)
                // Launch the app screen
                    AppScreen(
                        navController = navController,
                        auth = auth,
                        homeViewModel = homeViewModel,
                    )
                else
                // Launch the authentication screen
                    Authentication(
                        navController = navController,
                        authenticationViewModel = authenticationViewModel,
                        auth = auth
                    )
            }
        }
    }
}

// Switching to the Authentication screen
@Composable
private fun Authentication(
    navController: NavHostController,
    auth: FirebaseAuth,
    authenticationViewModel: AuthenticationViewModel
) {
    BottomNavItemModel(
        label = "Authentication",
        icon = Icons.Filled.AccountCircle,
        route = "authentication"
    )
    NavHost(navController = navController, startDestination = "authentication", builder = {
        composable("authentication") {
            AuthenticationScreen(
                authenticationViewModel = authenticationViewModel,
                auth = auth
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(navController: NavHostController, auth: FirebaseAuth, homeViewModel: HomeViewModel) {
    val database = Firebase.database.reference
    val cUser = auth.currentUser

    val selectedColor1 = remember { mutableStateOf(false) }
    val selectedColor2 = remember { mutableStateOf(false) }
    val selectedColor3 = remember { mutableStateOf(false) }

    val isShowBottomBar = remember { mutableStateOf(false) }

    CheckTheInterests(
        database = database,
        cUser = cUser,
        selectedColor1,
        selectedColor2,
        selectedColor3
    )

    Surface(color = MaterialTheme.colorScheme.surface) {
        // Scaffold Component
        Scaffold(
            // Bottom navigation
            bottomBar = { if (isShowBottomBar.value) BottomNavigationBar(navController = navController) },
            content = { padding ->
                NavHostContainer(
                    navController = navController,
                    padding = padding,
                    auth = auth,
                    homeViewModel = homeViewModel,
                    isShowBottomBar = isShowBottomBar,
                    selectedColor1 = selectedColor1,
                    selectedColor2 = selectedColor2,
                    selectedColor3 = selectedColor3
                )
            }
        )
    }

    // Запрет возврата к экрану Аутентификации
    //BackHandler(enabled = true) {}
}

// Screen Navigation
@Composable
private fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    auth: FirebaseAuth,
    selectedColor1: MutableState<Boolean>,
    selectedColor2: MutableState<Boolean>,
    selectedColor3: MutableState<Boolean>,
    isShowBottomBar: MutableState<Boolean>,
    homeViewModel: HomeViewModel
) {
    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    NavHost(
        navController = navController,
        // set the start destination as home
        startDestination = "splashScreen",
        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            // route : splash screen
            composable("splashScreen") {
                AnimatedSplashScreen(
                    navController = navController,
                    isShowBottomBar = isShowBottomBar
                )
            }
            // route : Home
            composable("home") {
                HomeScreen(
                    activity = activity,
                    context = context,
                    homeViewModel = homeViewModel
                )
            }
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
            // route : settings
            composable("settings") { SettingsScreen() }
        })
}

// Output of all screen icons
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        // set background color
        containerColor = NavigationBarDefaults.containerColor,
        contentColor = MaterialTheme.colorScheme.contentColorFor(containerColor),
        tonalElevation = NavigationBarDefaults.Elevation,
    ) {
        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route
        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->
            // Place the bottom nav items
            NavigationBarItem(
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