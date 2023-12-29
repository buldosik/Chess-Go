package com.example.chessgo.frontend.registration.sign_in

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
import com.example.chessgo.frontend.keyboardAsState
import com.example.chessgo.frontend.navigation.navigateToForgotPassword
import com.example.chessgo.frontend.navigation.navigateToSignUp
import com.example.chessgo.frontend.registration.EmailField
import com.example.chessgo.frontend.registration.PasswordField

private const val TAG = "SignInScreen"

/*
* head logic ui function which is invoked in SignInActivity
 */
@Composable
fun SignInScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val viewModel  = remember { SignInTools(navController, context) }

    val isKeyboardOpen by keyboardAsState() // true or false

    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }

    // Create an animated offset state for the InputTextFields
    val offsetState by animateFloatAsState(
        targetValue = if (!isKeyboardOpen) 0f else (-LocalDensity.current.density * 36), // Adjust the offset value as needed
        animationSpec = spring(),
        label = ""
    )
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        AnimatedVisibility(
            visible = !isKeyboardOpen,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.welcome_back),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .align(Alignment.CenterHorizontally)
                )

                val imagePainter = painterResource(id = R.drawable.enter_screen_figures)
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
        }

        EmailField(
            offset = offsetState,
            email = email
        ) { updatedEmail ->
            email = updatedEmail
        }
        PasswordField(
            offset = offsetState,
            password = password
        ) { updatedPassword ->
            password = updatedPassword
        }
        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = { navController.navigateToForgotPassword() },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 60.dp)
                .offset(y = offsetState.dp),
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        Spacer(Modifier.size(16.dp))

        LogInButton(
            offset = offsetState,
            onClick = { viewModel.onLoginClick(email, password) },
        )
        SignUpButton(
            offset = offsetState,
            onClick = { navController.navigateToSignUp() }
        )
    }
}

@Composable
fun LogInButton(offset: Float, onClick: () -> Unit){
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
            text = stringResource(id = R.string.log_in),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun SignUpButton(offset: Float, onClick: () -> Unit) {
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
            text = stringResource(id = R.string.sign_up),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SignInScreen()
}