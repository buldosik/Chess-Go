package com.example.chessgo.frontend.registration.sign_in

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessgo.frontend.MainActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
import com.example.chessgo.ui.theme.ChessgoTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity:  ComponentActivity() {


    private lateinit var auth: FirebaseAuth
    private var uiState by mutableStateOf(SignInUiState())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()
        setContent {
            ChessgoTheme {
                LoginForm {
                    val intent = Intent(applicationContext, SignUpActivity::class.java).apply {
                        putExtra("registration", false)
                    }

                    startActivity(intent)
                }
            }
        }
    }

    private fun login() {
        if (uiState.isNotEmpty()) {

            auth.signInWithEmailAndPassword(uiState.email, uiState.password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getPhotoUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }

    private fun getUserName(): String? {
        val user = auth.currentUser
        return if (user != null) {
            user.displayName
        } else "Failed to display an user's name"
    }

    @Composable
    fun LoginForm(onSignUpClick: () -> Unit) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //var email by remember { mutableStateOf("") }
            TextField(
                value = uiState.email,
                label = { Text(text = "Email") },
                onValueChange = {email ->  uiState = uiState.copy(email = email)}
            )

            Spacer(modifier = Modifier.height(20.dp))
            //var password by remember { mutableStateOf("") }
            TextField(
                value = uiState.password,
                label = { Text(text = "Password") },
                onValueChange = {password -> uiState = uiState.copy(password = password)}
            )

            Spacer(modifier = Modifier.height(1.dp))

            ClickableText(
                text = AnnotatedString("Forgot password?"),
                onClick = { },

                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(end = 60.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                ),

                )
            Spacer(modifier = Modifier.height(25.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue),
                    onClick = {
                        login()
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Login", style = TextStyle(fontSize = 25.sp), color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            val text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp
                    )
                ) {
                    append("No account? ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontSize = 14.sp
                    )
                ) {
                    append("Sign up here!")
                }
            }
            ClickableText(
                text = text,
                onClick = { onSignUpClick() },
            )
        }
    }
}