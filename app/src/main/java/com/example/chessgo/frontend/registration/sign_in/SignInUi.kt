package com.example.chessgo.frontend.registration.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
* head logic ui function which is invoked in SignInActivity
 */
@Composable
fun LoginForm(signInActivity: SignInActivity) {

    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var isRemember: Boolean by remember { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        EmailField(email) { updatedEmail ->
            email = updatedEmail
        }
        PasswordField(password) { updatedPassword ->
            password = updatedPassword
        }
        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = {signInActivity.toForgotPassword()},
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(end = 60.dp),
            style = TextStyle(fontSize = 14.sp,),
        )
        Spacer(Modifier.size(16.dp))

        LogInButton(
            onClick = { signInActivity.onLoginClick(email, password, isRemember) },
        )
        val text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp)) {
                append("No account? ")
            }
            withStyle(style = SpanStyle(color = Color.Blue, fontSize = 14.sp)) {
                append("Sign up here!")
            }
        }
        ClickableText(
            text = text,
            onClick = {signInActivity.toSignUp()},
        )
    }
}

@Composable
fun EmailField(email: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = { updatedEmail -> onValueChange(updatedEmail) },
        label = { Text("Email") },
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
    )
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    var passwordVisible: Boolean by remember { mutableStateOf(false) }
    TextField(
        value = password,
        label = { Text(text = "Password") },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                Icon(imageVector = image, description)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = {updatedPassword -> onPasswordChange(updatedPassword) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
        ),
    )
}

@Composable
fun LogInButton(onClick: () -> Unit, ){

    Button(
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue),
        onClick = onClick,
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .width(250.dp)
    ) {
        Text(text = "Login", style = TextStyle(fontSize = 25.sp), color = Color.White)
    }

}