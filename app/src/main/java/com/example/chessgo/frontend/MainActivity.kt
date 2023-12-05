package com.example.chessgo.frontend

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.frontend.mainmenu.MainMenuActivity
import com.example.chessgo.ui.theme.ChessgoTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            goToMainMenu()
            // User is signed in, navigate to the main screen
        } else {
            // No user is signed in, navigate to the auth screen
            setContent {
                ChessgoTheme {
                    MainApp(activity = this)
                }
            }
        }
    }
    fun goToMainMenu() {
        val intent = Intent(applicationContext, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}