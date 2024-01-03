package com.example.chessgo.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.frontend.navigation.AppNavHost
import com.example.chessgo.ui.theme.ChessGoTheme
import com.google.firebase.auth.FirebaseAuth

class EnterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()

        //WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        // Check if the user is already signed in
        val currentUser = auth.currentUser
        var isLogged = false
        if (currentUser != null) {
            ClientManager.initClient(currentUser)
            isLogged = true
        }

        setContent {
            ChessGoTheme () {
                AppNavHost(isLogged)
            }
        }
    }
}
