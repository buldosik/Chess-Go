package com.example.chessgo.frontend.registration.sign_up


import android.content.Intent
import android.os.Bundle
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

    private fun passwordValidator(password: String): Boolean {
        val minLength = 8
        val containsDigit = "(.*[0-9].*)"
        val containsLowerCase = "(.*[a-z].*)"
        val containsUpperCase = "(.*[A-Z].*)"
        val containsSpecialChar = "(.*[@#$%^&+=].*)"

        return password.length >= minLength &&
                password.matches(containsDigit.toRegex()) &&
                password.matches(containsLowerCase.toRegex()) &&
                password.matches(containsUpperCase.toRegex()) &&
                password.matches(containsSpecialChar.toRegex())
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
        // ToDo something like that
        // ClientManager.initClient(user.uid, user.displayName ,user.email)
        val intent = Intent(applicationContext, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    // ToDo put compose functions into another file, where will be only compose functions
    // ToDo add comments to elements, for reason without reading params of object to understand what is that object
    @Composable
    fun RegistrationForm(onSignInClick: () -> Unit){

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Registration", style = TextStyle(fontSize = 40.sp), modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp))

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = uiState.email,
                onValueChange = {email ->  uiState = uiState.copy(email = email) },
                label = { Text(text = "Email address") }
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = uiState.userName,
                onValueChange = {userName ->  uiState = uiState.copy(userName = userName)},
                label = { Text(text = "Username") }
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
                        println("Here")
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

            Spacer(modifier = Modifier.height(25.dp))

            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue),
                    onClick = {
                        if (passwordValidator(uiState.password)) {
                            createAccount()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Your password doesn't meet criteria",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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