package com.example.chessgo.frontend.registration.forgotPassword

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
import com.example.chessgo.frontend.keyboardAsState

@Composable
fun ForgotPasswordScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val sender = remember { ForgotPasswordTools(navController, context)}

    val isKeyboardOpen by keyboardAsState() // true or false
    // Create an animated offset state for the InputTextFields
    val offsetState by animateFloatAsState(
        targetValue = if (!isKeyboardOpen) 0f else (-LocalDensity.current.density * 36), // Adjust the offset value as needed
        animationSpec = spring(),
        label = ""
    )

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = offsetState.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password_Meme),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = R.string.forgot_password_tooltip),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(20.dp, 10.dp, 10.dp, 20.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            var email: String by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = { updated -> email = updated },
                label = { Text("Email") },
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                //colors = ButtonDefaults.outlinedButtonColors(),
                onClick = {
                    if (email.isNotEmpty()) {
                        sender.sendResetLink(email)
                    }
                    else {
                        Toast.makeText(context, "Email is empty", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.send_link),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ForgotPasswordScreen()
}