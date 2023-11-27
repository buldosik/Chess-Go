package com.example.chessgo.frontend.registration.sign_up


import android.content.Intent
import android.os.Bundle
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
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_up.SignUpUiState
import com.example.chessgo.frontend.mainmenu.MainMenuActivity
import com.example.chessgo.frontend.registration.sign_in.SignInActivity
import com.example.chessgo.ui.theme.ChessgoTheme

class SignUpActivity : ComponentActivity() {

    private var uiState by mutableStateOf(SignUpUiState())
    private var signUpViewModel = SignUpViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setContent {
            ChessgoTheme {
                RegistrationForm {
                    val intent = Intent(applicationContext, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun createAccount() {
        if (uiState.isNotEmpty()) {
            signUpViewModel.createAccount(uiState.email, uiState.userName, uiState.password)
        }

        signUpViewModel.signUpResult.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    Toast.makeText(
                        baseContext,
                        "Account creation success: ",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginOnSuccess()
                }
                is Results.Failure -> {
                    // Account creation failed, handle the error or provide feedback to the user
                    // You can access the error information with result.exception
                    Toast.makeText(
                        baseContext,
                        "Account creation failed: ${result.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun loginOnSuccess(){
        val intent = Intent(applicationContext, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Composable
    fun RegistrationForm(onSignInClick: () -> Unit){

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Registration", style = TextStyle(fontSize = 40.sp), modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))

            Spacer(modifier = Modifier.height(20.dp))
            //var email by remember { mutableStateOf("") }
            TextField(
                value = uiState.email,
                onValueChange = {email ->  uiState = uiState.copy(email = email) },
                label = { Text(text = "Email address") }
            )

            Spacer(modifier = Modifier.height(20.dp))
            //var userName by remember { mutableStateOf("") }
            TextField(
                value = uiState.userName,
                onValueChange = {userName ->  uiState = uiState.copy(userName = userName)},
                label = { Text(text = "Username") }
            )

            Spacer(modifier = Modifier.height(20.dp))
            //var password by remember { mutableStateOf("") }
            TextField(
                value = uiState.password,
                onValueChange = {password -> uiState = uiState.copy(password = password) },
                label = { Text(text = "Password") }
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
                        createAccount()
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Sign Up Free!", style = TextStyle(fontSize = 25.sp), color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            val text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp
                    )
                ) {
                    append("Have an account? ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontSize = 14.sp
                    )
                ) {
                    append("Sign in here!")
                }
            }
            ClickableText(
                text = text,
                onClick = { onSignInClick() },
            )

        }

    }
}