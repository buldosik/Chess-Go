package com.example.chessgo.frontend.registration.forgotPassword

import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.registration.forgotPassword.FPActivityManager

class ForgotPasswordViewModel (): ViewModel() {
    private val fpActivityManager = FPActivityManager()

    fun sendResetLink(email: String, callback: (String) -> Unit) {
        fpActivityManager.sendResetLink(email) {message ->
            callback(message)
        }
    }
}