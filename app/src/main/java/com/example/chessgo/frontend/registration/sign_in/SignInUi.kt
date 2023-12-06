package com.example.chessgo.frontend.registration.sign_in

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_in.SignInUiState

/*
* head logic ui function which is invoked in SignInActivity
 */
@Composable
fun LoginForm(
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginSuccess: () -> Unit,
) {

    val uiState = remember {
        mutableStateOf(SignInUiState())
    }
    val signInViewModel = remember { SignInViewModel() }
    val context = LocalContext.current

    LoginContent(
        uiState = uiState,
        onLoginClick = {
            if (uiState.value.isNotEmpty()) {
                signInViewModel.signInWithEmailAndPassword(uiState.value.email, uiState.value.password)
            }
        },
        onForgotPasswordClick = onForgotPasswordClick,
        onSignUpClick = onSignUpClick
    )

    signInViewModel.signInResult.observeAsState().value?.let { result ->
        when(result){
            is Results.Success -> {

                val user = result.data
                signInViewModel.initClient(user!!)

                onLoginSuccess()

                Toast.makeText(
                    context,
                    "Login success.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
            is Results.Failure -> {
                val exception = result.exception
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "createUserWithEmail:failure", exception)
                Toast.makeText(
                    context,
                    "Login failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}

/*
* contains main body of signIn form
 */
@Composable
fun LoginContent(
    uiState: MutableState<SignInUiState>,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit
){
    var passwordVisible: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
            value = uiState.value.email,
            label = { Text(text = "Email") },
            onValueChange = { email -> uiState.value = uiState.value.copy(email = email) }
        )
        // password ui function
        PasswordField(
            value = uiState.value.password,
            onValueChange = { password ->
                uiState.value = uiState.value.copy(password = password)
            },
            onToggleClick = { passwordVisible = !passwordVisible },
            passwordVisible = passwordVisible,
        )

        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = {onForgotPasswordClick()},
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(end = 60.dp),
            style = TextStyle(
                fontSize = 14.sp,
            ),
        )
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

        Spacer(Modifier.size(16.dp))

        LogInButton(
            onClick = onLoginClick,
            modifier =  Modifier
                .padding(0.dp, 0.dp, 0.dp, 16.dp)
                .width(250.dp)
        )

        ClickableText(
            text = text,
            onClick = {onSignUpClick()},
        )
    }
}


@Composable
fun LogInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Button(
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue),
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = "Login", style = TextStyle(fontSize = 25.sp), color = Color.White)
    }

}
@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    onToggleClick: () -> Unit,
    passwordVisible: Boolean
) {
    var password by remember { mutableStateOf(value) }
    TextField(
        value = password,
        label = { Text(text = "Password") },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = {
                onToggleClick()
            }) {
                Icon(imageVector = image, description)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = {
            onValueChange(it)
            it.also { password = it }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
        ),
    )
}