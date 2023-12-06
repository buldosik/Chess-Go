package com.example.chessgo.backend.registration.sign_in

import android.util.Log
import com.example.chessgo.backend.User
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.backend.registration.Results
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "SignInManager"

class SignInManager {
    private val auth: FirebaseAuth = Firebase.auth
    private var firestore = Firebase.firestore
    fun signInWithEmailAndPassword(email: String, password: String, callback: (Results<FirebaseUser?>) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = auth.currentUser
                initClient(user!!)
                callback(Results.Success(user))
            }
            .addOnFailureListener {exception ->
                callback(Results.Failure(exception))
            }
    }
    private fun FirebaseUser.toUser(): Task<User> {
        val userId = uid
        val docRef = firestore.collection("users").document(userId)

        return docRef.get().continueWithTask { task ->
            if (task.isSuccessful) {
                val userData = task.result?.data

                val username = userData?.get("userName") as? String ?: ""
                val email = userData?.get("email") as? String ?: ""
                val isModerator = userData?.get("isModerator") as? Boolean ?: false

                return@continueWithTask Tasks.forResult(User(uid = userId, username = username, email = email, isModerator = isModerator))
            } else {
                throw task.exception ?: Exception("Error fetching user data")
            }
        }
    }
    private fun initClient(user: FirebaseUser) {
        user.toUser().addOnSuccessListener { client ->
            // Successfully obtained the User object
            ClientManager.initClient(client)
        }.addOnFailureListener { exception ->
            // Handle failure, e.g., show an error message
            Log.e(TAG, "Error initializing client", exception)
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