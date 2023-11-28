package com.example.chessgo.frontend

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessgo.frontend.registration.sign_in.SignInActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
import com.example.chessgo.ui.theme.ChessgoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessgoTheme {
                GreetingScreen(onLoginClick = {
                    val intent = Intent(applicationContext, SignInActivity::class.java)
                    startActivity(intent)
                },) {
                    val intent = Intent(applicationContext, SignUpActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }


    @Composable
    fun GreetingScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome!",
                style = TextStyle(fontSize = 30.sp),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = { onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Log In")
            }

            Button(
                onClick = { onRegisterClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Register")
            }
        }
    }
}