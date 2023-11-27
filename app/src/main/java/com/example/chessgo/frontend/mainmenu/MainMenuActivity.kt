package com.example.chessgo.frontend.mainmenu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chessgo.ui.theme.ChessgoTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import com.example.chessgo.frontend.mainmenu.onlinegame.OnlineGameMenuActivity
import com.example.chessgo.frontend.mainmenu.realtimegame.RealLifeMenuActivity

class MainMenuActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setContent {
            ChessgoTheme {
                MainScreen(onIrlClick = {
                    val intent = Intent(applicationContext, RealLifeMenuActivity::class.java)
                    startActivity(intent)
                },) {
                    val intent = Intent(applicationContext, OnlineGameMenuActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    @Composable
    fun MainScreen(onIrlClick: () -> Unit, onOnlineClick: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { onIrlClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Play Irl")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { onOnlineClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Play Online")
            }
        }
    }


}