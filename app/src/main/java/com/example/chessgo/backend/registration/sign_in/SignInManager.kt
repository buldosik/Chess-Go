package com.example.chessgo.backend.registration.sign_in

import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.backend.registration.Results
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val TAG = "SignInManager"

class SignInManager {
    private val auth: FirebaseAuth = Firebase.auth
    fun signInWithEmailAndPassword(email: String, password: String, callback: (Results<FirebaseUser?>) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = auth.currentUser
                ClientManager.initClient(user!!)
                callback(Results.Success(user))
            }
            .addOnFailureListener {exception ->
                callback(Results.Failure(exception))
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