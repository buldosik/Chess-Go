package com.example.chessgo.frontend.mainmenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Wifi
import androidx.navigation.NavController
import com.example.chessgo.R
import com.example.chessgo.frontend.navigation.navigateToCreatingMenu
import com.example.chessgo.frontend.navigation.navigateToMyEventsMenu
import com.example.chessgo.frontend.navigation.navigateToOnlineMenu
import com.example.chessgo.frontend.navigation.navigateToSearchingMenu

class MainMenuItemsManager() {
    var itemsList = listOf(CreateEvents(), SearchEvents(), UserEvents(), OnlineGames())
    // Easily can make more complex OnClick functions
}

class CreateEvents : MainMenuItem(
    title = "Create Event",
    description = "Create somewhere new event!",
    icon = Icons.Default.Create,
    imageID = R.drawable.map_chess_icon_add,
) {
    override fun onClick(navController: NavController) {
        navController.navigateToCreatingMenu()
    }
}

class SearchEvents : MainMenuItem(
    title = "Search Events",
    description = "You can find something interesting here)",
    icon = Icons.Filled.Public,
    imageID = R.drawable.map_chess_icon_search,
) {
    override fun onClick(navController: NavController) {
        navController.navigateToSearchingMenu()
    }
}

class UserEvents : MainMenuItem(
    title = "My Events",
    description = "Don't forget about your games",
    icon = Icons.Default.Event,
    imageID = R.drawable.map_chess_icon_my,
) {
    override fun onClick(navController: NavController) {
        navController.navigateToMyEventsMenu()
    }
}

class OnlineGames : MainMenuItem(
    title = "Online Games",
    description = "Also you can play from home",
    icon = Icons.Default.Wifi,
    imageID = R.drawable.board,
) {
    override fun onClick(navController: NavController) {
        navController.navigateToOnlineMenu()
    }
}