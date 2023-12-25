package com.example.chessgo.frontend.mainmenu

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chessgo.R
import com.example.chessgo.frontend.navigation.navigateToEnteringScreen
import com.example.chessgo.frontend.navigation.navigateToIrlMenu
import com.example.chessgo.frontend.navigation.navigateToOnlineMenu
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainMenuScreen(
    navController: NavHostController,
) {

    val context = LocalContext.current
    val viewModel = remember { MainMenuViewModel() }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
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
                        viewModel.signOut()
                        navController.navigateToEnteringScreen()
                    }
                }
            )
        }
    ) {
        MainContent(
            onIrlClick = { navController.navigateToIrlMenu() },
            onOnlineClick = { navController.navigateToOnlineMenu() },
        )
    }
}

@Composable
fun MainContent(onIrlClick: () -> Unit, onOnlineClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Row() {
            //Spacer(modifier = Modifier.height(20.dp))
            // ToDo make it normal size & structure
            // That is just how it should be, not copy from chess.com)))
            val imagePainter = painterResource(id = R.drawable.map_chess_icon)
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
            Button(
                onClick = { onIrlClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Text(text = "Play Irl")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row() {
            // ToDo make it normal size & structure
            // That is just how it should be, not copy from chess.com)))
            val imagePainter = painterResource(id = R.drawable.board)
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    //.align(Alignment.CenterVertically)
            )
            Button(
                onClick = { onOnlineClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Text(text = "Play Online")
            }
        }
    }
}