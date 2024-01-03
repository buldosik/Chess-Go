package com.example.chessgo.frontend.registration.sign_up

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_up.SignUpManager
import com.example.chessgo.frontend.navigation.navigateToSignIn


private const val TAG = "SignUpViewModel"

class SignUpTools(
    val navController: NavHostController,
    val context: Context
) : ViewModel(){
    private val signUpManager = SignUpManager()

    fun onSignUpClick(email: String, username: String, password: String) {
        if(email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                context,
                "One or more fields are empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if(!validatePassword(password)) {
            Toast.makeText(
                context,
                "Your password doesn't meet criteria",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        createUser(email, username, password)
    }

    private fun createUser(email: String, username: String, password: String) {
        signUpManager.createUserWithEmailAndPassword(email, password) { result ->
            when (result) {
                is Results.Success -> {
                    Toast.makeText(
                        context,
                        "Verify email",
                        Toast.LENGTH_SHORT,
                    ).show()
                    signUpManager.createUserData(result.data, email, username)
                    signUpManager.signOut()
                    navController.navigateToSignIn()
                }

                is Results.Failure -> {
                    val exception = result.exception
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", exception)
                    Toast.makeText(
                        context,
                        "Registration failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
    /*
     * save user to database and creates companion object of user
     */


    private fun validatePassword(password: String): Boolean {
        val minLength = 8
        val containsDigit = "(.*[0-9].*)"
        val containsLowerCase = "(.*[a-z].*)"
        val containsUpperCase = "(.*[A-Z].*)"
        val containsSpecialChar = "(.*[@#$%^&+=.!].*)"

        return password.length >= minLength &&
                password.matches(containsDigit.toRegex()) &&
                password.matches(containsLowerCase.toRegex()) &&
                password.matches(containsUpperCase.toRegex()) &&
                password.matches(containsSpecialChar.toRegex())
    }
}