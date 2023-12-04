package com.example.chessgo.frontend.mainmenu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chessgo.ui.theme.ChessgoTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.example.chessgo.backend.registration.sign_in.SignInManager
import com.example.chessgo.frontend.MainActivity
import com.example.chessgo.frontend.irlMenu.IRLMenuActivity
import com.example.chessgo.frontend.mainmenu.onlinegame.OnlineGameMenuActivity
import kotlinx.coroutines.launch

class MainMenuActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setContent {
            ChessgoTheme {
                MainScreen(
                    onSignOutClick = {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    },
                    onIrlClick = {
                        val intent = Intent(applicationContext, IRLMenuActivity::class.java)
                        startActivity(intent)
                    },) {
                        val intent = Intent(applicationContext, OnlineGameMenuActivity::class.java)
                        startActivity(intent)
                    }
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    onSignOutClick: () -> Unit,
    onIrlClick: () -> Unit,
    onOnlineClick: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val signInManager = SignInManager()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader(modifier = Modifier)
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = "home",
                        title = "Home",
                        contentDescription = "Go to home screen",
                        icon = Icons.Default.Home
                    ),
                    MenuItem(
                        id = "settings",
                        title = "Settings",
                        contentDescription = "Go to settings screen",
                        icon = Icons.Default.Settings
                    ),
                    MenuItem(
                        id = "help",
                        title = "Help",
                        contentDescription = "Get help",
                        icon = Icons.Default.Info
                    ),
                    MenuItem(
                        id = "signOut",
                        title = "Sign Out",
                        contentDescription = "signOut",
                        icon = Icons.Default.ExitToApp
                    ),
                ),
                onItemClick = {
                    if (it.id == "signOut") {
                        signInManager.signOut()
                        onSignOutClick()
                    }
                }
            )
        }
    ) {
        mainContent(
            onIrlClick = onIrlClick,
            onOnlineClick = onOnlineClick
        )
    }
}

@Composable
fun mainContent(onIrlClick: () -> Unit, onOnlineClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onIrlClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Play Irl")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onOnlineClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Play Online")
        }
    }
}
