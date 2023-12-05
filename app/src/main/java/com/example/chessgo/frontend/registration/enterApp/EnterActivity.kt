package com.example.chessgo.frontend.registration.enterApp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.frontend.registration.sign_in.SignInActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
import com.example.chessgo.ui.theme.ChessgoTheme

class EnterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }
    fun goToSignIn() {
        val intent = Intent(applicationContext, SignInActivity::class.java)
        startActivity(intent)
    }


}