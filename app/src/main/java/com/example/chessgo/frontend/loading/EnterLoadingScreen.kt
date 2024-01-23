package com.example.chessgo.frontend.loading

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.frontend.LoadingScreen
import com.example.chessgo.frontend.navigation.navigateToMainMenu


@Composable
fun EnterLoadingScreen(navController: NavController = rememberNavController()) {
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
    LoadingScreen()
}
