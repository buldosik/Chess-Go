package com.example.chessgo.frontend.registration.sign_up


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.frontend.registration.sign_in.SignInActivity
import com.example.chessgo.ui.theme.ChessgoTheme

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessgoTheme {
                RegistrationForm {
                    val intent = Intent(applicationContext, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

}