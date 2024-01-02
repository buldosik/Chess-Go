package com.example.chessgo.frontend.registration.sign_up


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
import com.example.chessgo.frontend.keyboardAsState
import com.example.chessgo.frontend.navigation.navigateToSignIn
import com.example.chessgo.frontend.registration.EmailField
import com.example.chessgo.frontend.registration.PasswordField

private const val TAG = "SignInUI"

/*
* head logic ui function which is invoked in SignInActivity
 */
@Composable
fun SignUpScreen(navController: NavHostController = rememberNavController()) {
    var email: String by remember { mutableStateOf("") }
    var username: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }

    val context = LocalContext.current
    val assistant = remember { SignUpTools(navController, context) }

    val isKeyboardOpen by keyboardAsState() // true or false
    val offsetState by animateFloatAsState(
        targetValue = if (!isKeyboardOpen) 0f else (-LocalDensity.current.density * 36), // Adjust the offset value as needed
        animationSpec = spring(),
        label = ""
    )

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = !isKeyboardOpen,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                Text(
                    text = stringResource(id = R.string.create_account),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
                )
            }
            Spacer(Modifier.size(16.dp))
            EmailField(
                offset = offsetState,
                email = email
            ) { updated ->
                email = updated
            }
            UsernameField(
                offset = offsetState,
                username = username,
            ) { updated ->
                username = updated
            }
            PasswordField(
                offset = offsetState,
                password = password
            ) { updatedPassword ->
                password = updatedPassword
            }
            if(!isKeyboardOpen)
                Spacer(Modifier.size(64.dp))
            else
                Spacer(Modifier.size(16.dp))

            SignUpButton(
                offset = offsetState,
                onClick = {
                    assistant.onSignUpClick(email, username, password)
                }
            )

            AnimatedVisibility(
                visible = !isKeyboardOpen,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut(),
            ) {
                LogInButton(
                    onClick = { navController.navigateToSignIn() }
                )
            }
        }
    }
}

@Composable
fun UsernameField(offset: Float = 0f, username: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = username,
        onValueChange = { updated -> onValueChange(updated) },
        label = { Text("Username") },
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .offset(y = offset.dp),
    )
}

@Composable
fun SignUpButton(offset: Float = 0f, onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .width(250.dp)
            .offset(y = offset.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = stringResource(id = R.string.sign_up),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun LogInButton(offset: Float = 0f, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .width(200.dp)
            .offset(y = offset.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
        )
    ) {
        Text(
            text = stringResource(id = R.string.log_in),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SignUpScreen()
}