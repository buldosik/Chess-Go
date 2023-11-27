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
import androidx.lifecycle.LiveData
import com.example.chessgo.backend.registration.sign_in.SignInUiState
import com.example.chessgo.frontend.MainActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
import com.example.chessgo.ui.theme.ChessgoTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_in.SignInManager
import com.google.firebase.auth.FirebaseUser

class SignInActivity: ComponentActivity() {

    private var uiState by mutableStateOf(SignInUiState())
    private lateinit var signInViewModel :  SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInViewModel = SignInViewModel()
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
            signInViewModel.signInWithEmailAndPassword(uiState.email, uiState.password)
        }

        signInViewModel.signInResult.observe(this){result ->
            when(result){
                is Results.Success -> {
                    val user = result.data
                    // Handle success
                    onLoginSuccess(user)
                }
                is Results.Failure -> {
                    val exception = result.exception

                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", exception)
                    Toast.makeText(
                        baseContext,
                        "Login failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
    private fun onLoginSuccess(user: FirebaseUser?){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
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