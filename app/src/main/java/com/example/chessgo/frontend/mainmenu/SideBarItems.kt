package com.example.chessgo.frontend.mainmenu

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.NavController
import com.example.chessgo.frontend.navigation.navigateToEnteringScreen

private const val TAG = "SideBarItems"

class SideMenuItemsManager(signOut: () -> Unit) {
    var itemsList = listOf<SideMenuItem>(Settings(), Info(), SignOut(signOut))
    // Easy adding in future updates
    /*fun addSettings() {
        itemsList = itemsList + Settings()
    }
    fun addInfo() {
        itemsList += Info()
    }
    fun addSignOut(signOut: () -> Unit) {
        itemsList += SignOut(signOut)
    }*/
}

class Settings : SideMenuItem(
    title = "Settings",
    icon = Icons.Default.Settings) {
    override fun onClick(navController: NavController) {
        Log.d(TAG, "Settings")
        //TODO("Not yet implemented")
    }
}
class Info : SideMenuItem(
    title = "Info",
    icon = Icons.Default.Info) {
    override fun onClick(navController: NavController) {
        Log.d(TAG, "Info")
        //TODO("Not yet implemented")
    }
}
class SignOut(val signOut: () -> Unit) : SideMenuItem(
    title = "Sign out",
    icon = Icons.Default.ExitToApp) {
    override fun onClick(navController: NavController) {
        Log.d(TAG, "SignOut")
        signOut()
        navController.navigateToEnteringScreen()
    }
}
