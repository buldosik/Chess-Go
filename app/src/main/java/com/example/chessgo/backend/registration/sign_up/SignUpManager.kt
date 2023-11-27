package com.example.chessgo.backend.registration.sign_up

import android.content.ContentValues
import android.util.Log
import com.example.chessgo.backend.registration.Results
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpManager {
    private var auth  = Firebase.auth
    private var  firestore = Firebase.firestore
    fun createUserWithEmailAndPassword(email: String, userName: String, password: String, onComplete: (FirebaseUser?, UserProfileChangeRequest?) -> Unit) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(userName)
                        .build()
                    System.out.println("new boy")
                    onComplete(user, profileUpdates)
                } else {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(userName)
                        .build()
                    System.out.println("failed boy")
                    onComplete(null, null)
                }
            }
    }
    fun saveUserToDatabase(userName: String?, email: String?, uid: String?){

        val user = User(userName, email)
        if (uid != null) {
            firestore.collection("users").document(uid)
                .set(user)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "User data saved to Firestore")
                }
                .addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error saving user data to Firestore: $e")
                }
        }
    }
}



