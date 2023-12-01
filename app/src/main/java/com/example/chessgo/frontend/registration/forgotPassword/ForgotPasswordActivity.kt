package com.example.chessgo.frontend.registration.forgotPassword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class ForgotPasswordActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FPScreen(this)
        }
    }
}