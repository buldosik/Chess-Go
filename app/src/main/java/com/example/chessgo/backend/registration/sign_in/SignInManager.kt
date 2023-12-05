package com.example.chessgo.backend.registration.sign_in

import com.example.chessgo.backend.registration.Results
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class SignInManager {
    private val auth: FirebaseAuth = Firebase.auth
    // TODO Please rename this two parameters
    private val fireAuth = FirebaseAuth.getInstance()
    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)

    fun signInWithEmailAndPassword(email: String, password: String, onComplete: (Results<FirebaseUser?>) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(Results.Success(user))
                } else {
                    onComplete(Results.Failure(null))
                }
            }
    }

    fun getPhotoUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }

    fun getUserName(): String? {
        val user = auth.currentUser
        return if (user != null) {
            user.displayName
        } else "Failed to display an user's name"
    }
}