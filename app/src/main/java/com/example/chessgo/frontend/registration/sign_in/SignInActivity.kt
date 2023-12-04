package com.example.chessgo.frontend.registration.sign_in


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.ui.theme.ChessgoTheme
import com.example.chessgo.frontend.registration.forgotPassword.ForgotPasswordActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
class SignInActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessgoTheme {
                LoginForm(
                    onSignUpClick = {
                        val intent = Intent(applicationContext, SignUpActivity::class.java).apply {
                            putExtra("registration", false)
                        }
                        startActivity(intent)
                    }
                ) {
                    val intent = Intent(applicationContext, ForgotPasswordActivity::class.java).apply {
                        putExtra("registration", false)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}