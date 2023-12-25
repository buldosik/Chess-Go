package com.example.chessgo.frontend.registration.sign_in


import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_in.SignInManager
import com.example.chessgo.frontend.navigation.navigateToMainMenu
import com.google.firebase.auth.FirebaseUser

class SignInViewModel(
    val navController: NavHostController,
    val context: Context
) : ViewModel(){
    private val signInManager: SignInManager = SignInManager()

    private fun signInWithEmailAndPassword(email: String, password: String, callback: (Results<FirebaseUser?>) -> Unit) {
        signInManager.signInWithEmailAndPassword(email, password) { result ->
            callback(result)
        }
    }

    fun onLoginClick(email: String, password: String, isRemember: Boolean) {
        if (email.isEmpty() || password.isEmpty()) {
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
                    Toast.makeText(
                        context,
                        "Login failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }


}