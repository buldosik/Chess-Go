package com.example.chessgo.backend.registration.sign_in

import android.content.ContentValues.TAG
import android.util.Log
import com.example.chessgo.backend.registration.Results
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInManager {
    private val auth: FirebaseAuth = Firebase.auth
    private var firestore = Firebase.firestore
    fun signInWithEmailAndPassword(email: String, password: String, onComplete: (Results<FirebaseUser?>) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = auth.currentUser
                onComplete(Results.Success(user))
            }
            .addOnFailureListener {exception ->
                onComplete(Results.Failure(exception))
            }
    }
    fun getUserFromFirebase(
        user: FirebaseUser,
        onSuccess: (userData: Map<String, Any>) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ){
        val docRef = firestore.collection("users").document(user.uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userData = document.data

                    if (userData != null) {
                        // Create a new map with "uid" added to userData
                        val userDataWithUid = userData + ("uid" to user.uid)

                        onSuccess(userDataWithUid)
                    } else {
                        // Handle the case where userData is null
                        onFailure(NoSuchElementException("No such document"))
                    }
                }
            }
            .addOnFailureListener { exception ->
                System.out.println("2")
                Log.d(TAG, "get failed with ", exception)
            }
    }
    fun getPhotoUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }
}