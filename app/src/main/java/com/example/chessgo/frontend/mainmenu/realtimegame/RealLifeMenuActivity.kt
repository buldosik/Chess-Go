package com.example.chessgo.frontend.mainmenu.realtimegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.example.chessgo.ui.theme.ChessgoTheme

class RealLifeMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setContent {
            ChessgoTheme {
                Text("irl game menu")
            }
        }
    }
}