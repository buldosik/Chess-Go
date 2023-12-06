package com.example.chessgo.frontend.registration.sign_in


import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_in.SignInManager
import com.google.firebase.auth.FirebaseUser

class SignInViewModel : ViewModel(){
    private val signInManager: SignInManager = SignInManager()

    fun signInWithEmailAndPassword(email: String, password: String, callback: (Results<FirebaseUser?>) -> Unit) {
        signInManager.signInWithEmailAndPassword(email, password) { result ->
            callback(result)
        }
    }
}