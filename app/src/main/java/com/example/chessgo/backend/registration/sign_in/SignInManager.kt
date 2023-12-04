package com.example.chessgo.backend.registration.sign_in

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.chessgo.backend.registration.Results
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInManager {
    private val auth: FirebaseAuth = Firebase.auth
    // TODO Please rename this two parameters
    private val fireAuth = FirebaseAuth.getInstance()
    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    // TODO Also need review
    val curUser: StateFlow<FirebaseUser?> = _user

    fun signInWithEmailAndPassword(email: String, password: String, onComplete: (Results<FirebaseUser?>) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
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

    fun signOut() {
        fireAuth.signOut()
        _user.value = null
    }

}