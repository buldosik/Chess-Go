package com.example.chessgo.frontend.registration.sign_up


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chessgo.frontend.navigation.navigateToSignIn
import com.example.chessgo.frontend.registration.EmailField
import com.example.chessgo.frontend.registration.PasswordField

private const val TAG = "SignInUI"

/*
* head logic ui function which is invoked in SignInActivity
 */
@Composable
fun SignUpScreen(navController: NavHostController) {
    var email: String by remember { mutableStateOf("") }
    var username: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var isRemember: Boolean by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val viewModel = remember { SignUpTools(navController, context) }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Registration",
                style = TextStyle(fontSize = 40.sp),
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
            EmailField(email = email) { updated ->
                email = updated
            }
            UsernameField(username) { updated ->
                username = updated
            }
            PasswordField(password = password) { updatedPassword ->
                password = updatedPassword
            }
            Spacer(Modifier.size(16.dp))

            SignUpButton(
                onClick = {
                    if (viewModel.passwordValidator(password)) {
                        viewModel.onSignUpClick(email, username, password, isRemember)
                    } else {
                        // ToDo Trello -> Update password creation
                        Toast.makeText(
                            context,
                            "Your password doesn't meet criteria",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 16.dp)
                    .width(250.dp)

            )

            SignInLink(
                onClick = { navController.navigateToSignIn() }
            )
        }
    }
}

@Composable
fun UsernameField(username: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = username,
        onValueChange = { updated -> onValueChange(updated) },
        label = { Text("Username") },
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
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