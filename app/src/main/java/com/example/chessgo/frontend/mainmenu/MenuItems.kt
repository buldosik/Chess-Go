package com.example.chessgo.frontend.mainmenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.chessgo.R

interface MenuItemClick {
    fun onClick(navController: NavController)
}

abstract class MainMenuItem(
    val title: String = "",
    val description: String = "",
    val icon: ImageVector = Icons.Default.Star,
    val imageID: Int = R.drawable.map_chess_icon,
) : MenuItemClick

abstract class SideMenuItem(
    val title: String = "",
    val icon: ImageVector = Icons.Default.Star,
) : MenuItemClick