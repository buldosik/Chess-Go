package com.example.chessgo.frontend.registration.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chessgo.frontend.navigation.navigateToForgotPassword
import com.example.chessgo.frontend.navigation.navigateToSignUp
import com.example.chessgo.frontend.registration.EmailField
import com.example.chessgo.frontend.registration.PasswordField

/*
* head logic ui function which is invoked in SignInActivity
 */
@Composable
fun SignInScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel  = remember { SignInViewModel(navController, context) }

    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var isRemember: Boolean by remember { mutableStateOf(false) }
    Surface {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
                onClick = { navController.navigateToForgotPassword() },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 60.dp),
                style = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 14.sp,),
            )
            Spacer(Modifier.size(16.dp))

            LogInButton(
                onClick = { viewModel.onLoginClick(email, password, isRemember) },
            )
            SignUpLink(
                onClick = { navController.navigateToSignUp() }
            )
        }
    }
}

@Composable
fun LogInButton(onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .width(250.dp)
    ) {
        Text(text = "Login", style = TextStyle(fontSize = 25.sp), color = Color.White)
    }
}

@Composable
fun SignUpLink(onClick: () -> Unit) {
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = MaterialTheme.colors.onBackground, fontSize = 14.sp)
        ) {
            append("Have an account? ")
        }
        withStyle(
            style = SpanStyle(color = MaterialTheme.colors.primary, fontSize = 14.sp)
        ) {
            append("Sign up here!")
        }
    }
    ClickableText(
        text = text,
        onClick = { onClick() },
        modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp)
    )
}