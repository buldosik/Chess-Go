package com.example.chessgo.frontend.registration.sign_up

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_up.SignUpUiState

/*
* head logic ui function which is invoked in SignInActivity
 */
@Composable
fun RegistrationForm(
    onSignInClick: () -> Unit
) {
    val uiState = remember { mutableStateOf(SignUpUiState()) }
    val signUpViewModel = remember { SignUpViewModel() }
    val context = LocalContext.current

    SignUpContent(
        uiState = uiState,
        onSignInClick = { onSignInClick() },
        onSignUpClick = {
            if (signUpViewModel.passwordValidator(uiState.value.password)) {
                signUpViewModel.createAccount(uiState.value.email, uiState.value.userName, uiState.value.password)
            } else {

                Toast.makeText(
                    context,
                    "Your password doesn't meet criteria",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    signUpViewModel.signUpResult.observeAsState().value?.let { result ->
        when (result) {
            is Results.Success -> {
                Toast.makeText(
                    LocalContext.current,
                    "Account creation success: ",
                    Toast.LENGTH_SHORT
                ).show()
                onSignInClick()
            }
            is Results.Failure -> {
                Toast.makeText(
                    LocalContext.current,
                    "Account creation failed: ${result.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun SignUpContent(
    uiState: MutableState<SignUpUiState>,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registration",
            style = TextStyle(fontSize = 40.sp),
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
        )

        TextField(
            value = uiState.value.email,
            onValueChange = { email -> uiState.value = uiState.value.copy(email = email) },
            label = { Text(text = "Email address") },
            modifier = Modifier

                .padding(0.dp, 0.dp, 0.dp, 16.dp)
        )

        TextField(
            value = uiState.value.userName,
            onValueChange = { userName -> uiState.value = uiState.value.copy(userName = userName) },
            label = { Text(text = "Username") },
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 16.dp)
        )

        PasswordTextField(
            value = uiState.value.password,
            onValueChange = { password -> uiState.value = uiState.value.copy(password = password) },
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 16.dp)
        )

        SignUpButton(
            onClick = onSignUpClick,
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 16.dp)
                .width(250.dp)

        )

        SignInLink(
            onClick = onSignInClick
        )
    }
}


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Password") },
        trailingIcon = {
            val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = icon, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier
    )
}

@Composable
fun SignUpButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue)
    ) {
        Text(text = "Sign Up Free!", style = TextStyle(fontSize = 25.sp), color = Color.White)
    }
}

@Composable
fun SignInLink(onClick: () -> Unit) {
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(fontSize = 14.sp)
        ) {
            append("Have an account? ")
        }
        withStyle(
            style = SpanStyle(color = Color.Blue, fontSize = 14.sp)
        ) {
            append("Sign in here!")
        }
    }
    ClickableText(
        text = text,
        onClick = { onClick() },
        modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp)
    )
}