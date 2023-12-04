package com.example.chessgo.backend.registration.sign_up

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpManager {
    private var auth  = Firebase.auth
    private var firestore = Firebase.firestore
    fun createUserWithEmailAndPassword(email: String, userName: String, password: String, onComplete: (FirebaseUser?, UserProfileChangeRequest?) -> Unit) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if( task.isSuccessful){
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(userName)
                        .build()
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { verificationTask ->
                            if(verificationTask.isSuccessful){
                                onComplete(user, profileUpdates)
                            } else {
                                onComplete(null, null)
                            }
                        }
                }
            }
    }
    fun saveUserToDatabase(userName: String?, email: String?, uid: String?, isModerator: Boolean){

        val dataToAdd = mapOf(
            "username" to userName,
            "email" to email,
            "isModerator" to isModerator

        )

        if ( uid != null){
            firestore.collection("users").document(uid)
                .set(dataToAdd)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "User data saved to Firestore")
                }
                .addOnFailureListener { e ->
                    Log.e(ContentValues.TAG, "Error saving user data to Firestore: $e")
                }
        }
    }
}



