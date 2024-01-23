package com.example.chessgo.frontend.onlinegame.random

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.frontend.LoadingScreen
import com.example.chessgo.frontend.onlinegame.game.ChessBoardBox
import com.example.chessgo.frontend.onlinegame.game.ChessBoardViewModel

@Composable
fun RandomOnlineGameScreen(navController: NavHostController = rememberNavController()) {
    val viewModel = remember { ChessBoardViewModel() }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var temp by remember {
        mutableStateOf(false)
    }
    val toggleLoading : () -> Unit = {
        isLoading = !isLoading
    }
    val toggleTemp : () -> Unit = {
        temp = !temp
    }
    LaunchedEffect(Unit) {
        viewModel.init(ClientManager.currentLobbyId, toggleLoading, toggleTemp)
    }
    Surface (
        modifier = Modifier.fillMaxSize()
    ) {
        if(!isLoading)
            LoadingScreen()
        else
            Box(
                contentAlignment = Alignment.Center
            ){
                ChessBoardBox(Modifier, viewModel)
            }
    }
}