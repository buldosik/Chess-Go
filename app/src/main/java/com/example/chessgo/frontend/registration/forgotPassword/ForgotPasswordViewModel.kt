package com.example.chessgo.frontend.registration.forgotPassword

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chessgo.backend.registration.forgotPassword.FPActivityManager
import com.example.chessgo.frontend.navigation.navigateToSignIn

class ForgotPasswordViewModel (
    private val navController: NavHostController,
    private val context: Context
) : ViewModel() {
    private val fpActivityManager = FPActivityManager()

    fun sendResetLink(email: String) {
        fpActivityManager.sendResetLink(email) {message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (message == "Success") {
                navController.navigateToSignIn()
            }
        }
    }
}