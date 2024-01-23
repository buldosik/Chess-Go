package com.example.chessgo.frontend.onlinegame

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.frontend.navigation.navigateToRandomOnlineGame

private const val TAG = "OnlineMenuScreen"

@Composable
fun OnlineMenuScreen(navController: NavHostController  = rememberNavController()) {
    val context = LocalContext.current
    val viewModel = remember { OnlineMenuViewModel() }
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button (
                modifier = Modifier,
                onClick = {viewModel.startMatchmaking {
                    ClientManager.currentLobbyId = it
                    Log.d(TAG, "Successful get lobby id, $it")
                    navController.navigateToRandomOnlineGame()
                }}
            ) {
                Text("Play Random")
            }
            Button (
                modifier = Modifier,
                onClick = {
                    Toast.makeText(
                        context,
                        "In Future Updates",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                Text("Play Rating")
            }

            Button (
                modifier = Modifier,
                onClick = {
                    Toast.makeText(
                        context,
                        "In Future Updates",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                Text("Play vs Friend")
            }
        }
    }
}
