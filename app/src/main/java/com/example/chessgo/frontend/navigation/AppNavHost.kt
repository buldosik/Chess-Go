package com.example.chessgo.frontend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.backend.registration.sign_in.SignInManager

@Composable
fun AppNavHost(isLogged: Boolean) {
    val navController = rememberNavController()

    // Use the SetupNavigation composable from AppNavigation.kt
    SetupNavigation(navController = navController, isLogged)

    // Add any additional UI elements or composables here
}

@Composable
fun SetupNavigation(navController: NavHostController, isLogged: Boolean) {
    var initScreen = screens.first().route
    if(isLogged){
        initScreen = screens[4].route
    }
    NavHost(
        navController = navController,
        startDestination = initScreen
    ) {
        // Use a loop to set up composable destinations
        screens.forEach { screen ->
            composable(screen.route) {
                // Call a function to handle each screen
                HandleScreen(screen, navController)
            }
        }
    }
}
