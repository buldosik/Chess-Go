package com.example.chessgo.backend.registration.forgotPassword

import com.google.firebase.auth.FirebaseAuth

class FPActivityManager {
    fun sendResetLink(email: String, callback: (String) -> Unit) {
        var message = ""
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnSuccessListener { message = "Success" }
            .addOnFailureListener { task -> message = task.message.toString() }
            .addOnCompleteListener {
                callback(message)
            }
    }
}