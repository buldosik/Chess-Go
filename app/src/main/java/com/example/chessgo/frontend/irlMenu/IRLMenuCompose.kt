package com.example.chessgo.frontend.irlMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chessgo.frontend.navigation.navigateToCreatingMenu
import com.example.chessgo.frontend.navigation.navigateToMyEventsMenu
import com.example.chessgo.frontend.navigation.navigateToSearchingMenu

@Composable
fun IRLMenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigateToCreatingMenu() }) {
            Text(text = "Create Event", fontSize = 16.sp)
        }

        Button(onClick = { navController.navigateToSearchingMenu() }) {
            Text(text = "Search Events", fontSize = 16.sp)
        }

        Button(onClick = { navController.navigateToMyEventsMenu() }) {
            Text(text = "My Events", fontSize = 16.sp)
        }
    }
}
