package com.example.chessgo.frontend.registration.sign_in


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_in.SignInManager
import com.example.chessgo.frontend.mainmenu.MainMenuActivity
import com.example.chessgo.frontend.registration.forgotPassword.ForgotPasswordActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
import com.example.chessgo.ui.theme.ChessgoTheme
import com.google.firebase.auth.FirebaseUser

class SignInActivity: ComponentActivity() {

    private val signInManager: SignInManager = SignInManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessgoTheme {
                LoginForm(this)
            }
        }
    }
    fun toSignUp() {
        val intent = Intent(applicationContext, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun toForgotPassword() {
        val intent = Intent(applicationContext, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun toMainMenu() {
        val intent = Intent(applicationContext, MainMenuActivity::class.java)
        startActivity(intent)
    }

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
                    toMainMenu()
                }
                is Results.Failure -> {
                    val exception = result.exception
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", exception)
                    Toast.makeText(
                        this.baseContext,
                        "Login failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
}