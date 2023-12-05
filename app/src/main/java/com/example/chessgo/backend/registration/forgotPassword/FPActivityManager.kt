package com.example.chessgo.backend.registration.forgotPassword

import android.widget.Toast
import com.example.chessgo.frontend.registration.forgotPassword.ForgotPasswordActivity
import com.google.firebase.auth.FirebaseAuth

class FPActivityManager {
    fun sendResetLink(email: String, activity: ForgotPasswordActivity) {
        var message = ""
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnSuccessListener { message = "Success" }
            .addOnFailureListener { task -> message = task.message.toString() }
            .addOnCompleteListener {
                Toast.makeText(activity.baseContext, message, Toast.LENGTH_SHORT).show()
                if (message == "Success") {
                    activity.finish()
                }
            }
    }
}