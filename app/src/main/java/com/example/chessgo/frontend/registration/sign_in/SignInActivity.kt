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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.backend.registration.sign_in.SignInUiState
import com.example.chessgo.ui.theme.ChessgoTheme
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.frontend.mainmenu.MainMenuActivity
import com.example.chessgo.frontend.registration.forgotPassword.ForgotPasswordActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
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
                LoginForm(
                    onSignUpClick = {
                        val intent = Intent(applicationContext, SignUpActivity::class.java).apply {
                            putExtra("registration", false)
                        }
                        startActivity(intent)
                    }
                ) {
                    val intent = Intent(applicationContext, ForgotPasswordActivity::class.java).apply {
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
        // ToDo something like that
        // ClientManager.initClient(user.uid, user.displayName ,user.email)
        val intent = Intent(applicationContext, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    // ToDo put compose functions into another file, where will be only compose functions
    // ToDo add comments to elements, for reason without reading params of object to understand what is that object
    @Composable
    fun LoginForm(
        onSignUpClick: () -> Unit,
        onForgotPasswordClick: () -> Unit
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = uiState.email,
                label = { Text(text = "Email") },
                onValueChange = {email ->  uiState = uiState.copy(email = email)}
            )

            Spacer(modifier = Modifier.height(20.dp))

            var passwordVisible: Boolean by remember { mutableStateOf(false) }
            TextField(
                value = uiState.password,
                label = { Text(text = "Password") },
                trailingIcon = {
                    var image = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                        image = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    }) {
                        Icon(imageVector = image, description)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = {password -> uiState = uiState.copy(password = password)},
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                ),
            )

            Spacer(modifier = Modifier.height(1.dp))

            ClickableText(
                text = AnnotatedString("Forgot password?"),
                onClick = { onForgotPasswordClick() },

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