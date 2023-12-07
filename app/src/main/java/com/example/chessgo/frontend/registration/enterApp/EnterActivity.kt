package com.example.chessgo.frontend.registration.enterApp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.frontend.mainmenu.MainMenuActivity
import com.example.chessgo.frontend.registration.sign_in.SignInActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
import com.example.chessgo.ui.theme.ChessgoTheme
import com.google.firebase.auth.FirebaseAuth

class EnterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()

        // Check if the user is already signed in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            ClientManager.initClient(currentUser)
            goToMainMenu()
        }

        setContent {
            ChessgoTheme {
                GreetingScreen(this)
            }
        }
    }
    // In future more easily replace with nav controller
    fun goToSignUp() {
        val intent = Intent(applicationContext, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToSignIn() {
        val intent = Intent(applicationContext, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToMainMenu() {
        // User is already signed in, navigate to the main activity or desired screen
        startActivity(Intent(this, MainMenuActivity::class.java))
        finish()
    }


}