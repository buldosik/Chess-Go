package com.example.chessgo.frontend.registration.forgotPassword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.backend.registration.forgotPassword.FPActivityManager


class ForgotPasswordActivity: ComponentActivity() {
    private val fpActivityManager = FPActivityManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FPScreen(this)
        }
    }
    fun sendResetLink(email: String) {
        fpActivityManager.sendResetLink(email, this)
    }
}