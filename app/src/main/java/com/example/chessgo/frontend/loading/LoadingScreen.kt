package com.example.chessgo.frontend.loading

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
import com.example.chessgo.frontend.navigation.navigateToMainMenu


@Composable
fun LoadingScreen(navController: NavController = rememberNavController()) {
    val context = LocalContext.current
    val viewModel = remember { LoadingViewModel() }
    LaunchedEffect(Unit) {
        viewModel.initClient(object: InitClientCallback {
            override fun onSuccess() {
                navController.navigateToMainMenu()
            }
            override fun onFail() {
                Toast.makeText(
                    context,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
    Content()
}

@Preview
@Composable
private fun Content() {
    Surface (
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val imagePainter = painterResource(id = R.drawable.enter_screen_figures)
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.size(32.dp))
            CircularProgressIndicator(
                modifier = Modifier.width(128.dp)
            )
        }
    }
}
