package com.example.chessgo.frontend.registration.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_up.SignUpManager
import com.example.chessgo.frontend.navigation.navigateToSignIn


private const val TAG = "SignUpViewModel"

class SignUpViewModel(
    val navController: NavHostController,
) : ViewModel(){
    private val signUpManager = SignUpManager()

    fun onSignUpClick(email: String, username: String, password: String, callback: (String) -> Unit) {
        if(email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            callback("One or more fields are empty")
            return
        }
        if(!validatePassword(password)) {
            callback("Your password doesn't meet criteria")
            return
        }
        createUser(email, username, password) {message ->
            callback(message)
        }
    }

    private fun createUser(email: String, username: String, password: String, callback: (String) -> Unit) {
        signUpManager.createUserWithEmailAndPassword(email, password) { result ->
            when (result) {
                is Results.Success -> {
                    callback("Verify email")
                    signUpManager.createUserData(result.data, email, username)
                    signUpManager.signOut()
                    navController.navigateToSignIn()
                }

                is Results.Failure -> {
                    val exception = result.exception
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", exception)
                    callback("Registration failed")
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