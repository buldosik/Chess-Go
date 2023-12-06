package com.example.chessgo.frontend.registration.sign_in


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.User
import com.example.chessgo.backend.global.ClientManager.Companion.initClient
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_in.SignInManager
import com.google.firebase.auth.FirebaseUser

class SignInViewModel : ViewModel(){
    private val signInManager: SignInManager = SignInManager()

    fun signInWithEmailAndPassword(email: String, password: String, callback: (Results<FirebaseUser?>) -> Unit) {
        signInManager.signInWithEmailAndPassword(email, password) { result ->
            when(result) {
                is Results.Success -> {
                    val user = result.data
                    initClient(user!!)
                    callback(result)
                }
                is Results.Failure -> {
                    callback(result)
                }
            }
        }
    }
    /*
     * init client companion object if get user from database was successful
     */
    fun initClient(user: FirebaseUser) {
        signInManager.getUserFromFirebase(
            user = user,
            onSuccess = { userData ->
                val userId = userData["uid"] as? String ?: ""
                val username = userData["userName"] as? String ?: ""
                val email = userData["email"] as? String ?: ""
                val isModerator = userData["isModeratore"] as? Boolean ?: false
                val client = User(
                    userId = userId,
                    username = username,
                    email = email,
                    isModerator = isModerator
                )
                client.initClient()
            },
            onFailure = { exception ->
                // Handle failure, e.g., show an error message
                Log.e(TAG, "Error getting user data", exception)
            }
        )
    }

}