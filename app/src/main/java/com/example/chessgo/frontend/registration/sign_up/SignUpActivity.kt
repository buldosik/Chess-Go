package com.example.chessgo.frontend.registration.sign_up


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.backend.User
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_up.SignUpManager
import com.example.chessgo.frontend.registration.sign_in.SignInActivity
import com.example.chessgo.ui.theme.ChessgoTheme
import com.google.firebase.auth.FirebaseUser

private const val TAG = "SignUpActivity"

class SignUpActivity : ComponentActivity() {
    private val signUpManager = SignUpManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessgoTheme {
                RegistrationForm (
                    this,
                )
            }
        }
    }
    fun toSignIn() {
        val intent = Intent(applicationContext, SignInActivity::class.java).apply {
            putExtra("registration", false)
        }
        startActivity(intent)
    }

    fun onSignUpClick(email: String, username: String, password: String, isRemember: Boolean) {
        signUpManager.createUserWithEmailAndPassword(email, password) { result ->
            when (result) {
                is Results.Success -> {
                    Toast.makeText(
                        this.baseContext,
                        "Verify email",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateDatabase(result.data, email, username)
                    toSignIn()
                }

                is Results.Failure -> {
                    val exception = result.exception
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", exception)
                    Toast.makeText(
                        this.baseContext,
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
    private fun updateDatabase(user: FirebaseUser?, email: String, userName: String){
        val client = User(uid = user!!.uid, username = userName, email = email, isModerator = false)
        signUpManager.saveUserToDatabase(client)
    }
}