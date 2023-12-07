package com.example.chessgo.frontend.registration.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FPScreen(
    forgotPasswordActivity: ForgotPasswordActivity,
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Forgot password?",
            style = TextStyle(fontSize = 18.sp)
        )
        Text(
            text = "Please, enter your email and we will send you link for password reset",
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
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue),
            onClick = {
                if (email.isNotEmpty()) {
                    forgotPasswordActivity.sendResetLink(email)
                }
            },
            modifier = Modifier
                .width(250.dp)
        ) {
            Text(text = "Submit", style = TextStyle(fontSize = 25.sp), color = Color.White)
        }
    }
}
