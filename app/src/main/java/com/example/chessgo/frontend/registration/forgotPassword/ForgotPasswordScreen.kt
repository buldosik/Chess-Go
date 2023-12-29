package com.example.chessgo.frontend.registration.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R

@Composable
fun ForgotPasswordScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val viewModel = remember { ForgotPasswordTools(navController, context)}

    Surface(

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password_Meme),
                style = TextStyle(fontSize = 18.sp)
            )
            Text(
                text = stringResource(id = R.string.forgot_password_tooltip),
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(20.dp, 10.dp, 10.dp, 20.dp),
                textAlign = TextAlign.Center
            )

            var email: String by remember { mutableStateOf("") }
            TextField(
                value = email,
                label = { Text(text = "Email") },
                onValueChange = { email = it }
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                //colors = ButtonDefaults.outlinedButtonColors(),
                onClick = {
                    if (email.isNotEmpty()) {
                        viewModel.sendResetLink(email)
                    }
                },
                modifier = Modifier
                    .width(250.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.send_link),
                    style = TextStyle(fontSize = 25.sp),
                    color = Color.White)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ForgotPasswordScreen()
}