package com.example.chessgo.backend.registration.sign_up

import android.util.Log
import com.example.chessgo.backend.User
import com.example.chessgo.backend.registration.Results
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "SignUpManager"

class SignUpManager {
    private var auth  = Firebase.auth
    private var firestore = Firebase.firestore
    fun createUserWithEmailAndPassword(email: String, password: String, callback: (Results<FirebaseUser?>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnFailureListener {
                            callback(Results.Failure(it))
                        }
                    callback(Results.Success(user))
            }
            .addOnFailureListener {
                callback(Results.Failure(it))
            }
    }
    fun createUserData(user: FirebaseUser?, email: String, userName: String){
        val client = User(uid = user!!.uid, username = userName, email = email, isModerator = false)
        saveUserToDatabase(client)
    }
    private fun saveUserToDatabase(user: User){
        firestore.collection("users").document(user.uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User data saved to Firestore")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error saving user data to Firestore: $e")
            }
    }
    fun signOut() {
        auth.signOut()
    }
}



