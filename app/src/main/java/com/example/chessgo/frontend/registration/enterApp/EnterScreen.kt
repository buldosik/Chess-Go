package com.example.chessgo.frontend.registration.enterApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
import com.example.chessgo.frontend.navigation.navigateToSignIn
import com.example.chessgo.frontend.navigation.navigateToSignUp

@Composable
fun GreetingScreen(navController: NavHostController = rememberNavController()) {
    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.enter_line),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge,
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

            Button(
                onClick = { navController.navigateToSignIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 32.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.log_in),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Button(
                onClick = { navController.navigateToSignUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 32.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
}


@Preview
@Composable
fun Preview() {
    GreetingScreen()
}