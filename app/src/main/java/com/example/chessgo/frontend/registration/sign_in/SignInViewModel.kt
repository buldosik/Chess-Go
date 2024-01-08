package com.example.chessgo.frontend.registration.sign_in


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_in.SignInManager
import com.example.chessgo.frontend.navigation.navigateToMainMenu
import com.google.firebase.auth.FirebaseUser

class SignInViewModel(
    val navController: NavHostController
): ViewModel() {
    private val signInManager: SignInManager = SignInManager()

    private fun signInWithEmailAndPassword(email: String, password: String, callback: (Results<FirebaseUser?>) -> Unit) {
        signInManager.signInWithEmailAndPassword(email, password) { result ->
            callback(result)
        }
    }

    fun onLoginClick(email: String, password: String, callback: (String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.w(ContentValues.TAG, "createUserWithEmail:no password or email")
            var info = ""
            if (email.isEmpty() && password.isEmpty())
                info += "Enter email\nEnter password"
            else if (email.isEmpty())
                info += "Enter email"
            else if (password.isEmpty())
                info += "Enter password"
            callback(info)
            return
        }
        signInWithEmailAndPassword(email, password) { result ->
            when(result){
                is Results.Success -> {
                    navController.navigateToMainMenu()
                }
                is Results.Failure -> {
                    val exception = result.exception
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", exception)
                    callback("Login failed")
                }
            }
        }
    }
}