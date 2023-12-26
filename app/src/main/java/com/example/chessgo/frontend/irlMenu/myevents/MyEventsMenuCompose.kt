package com.example.chessgo.frontend.irlMenu.myevents

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.chessgo.backend.GameIRL
import java.text.SimpleDateFormat

@Composable
fun MyEventsScreen(navController: NavHostController) {
    val viewModel = remember { ListingViewModel() }
    var isGameInfo by remember { mutableStateOf(false) }
    viewModel.getGames()

    val toggleGameInfo: () -> Unit = {
        isGameInfo = !isGameInfo
    }

    ConstraintLayout {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(viewModel.games) {
                    GameItem(gameIRL = it, viewModel, toggleGameInfo)
                }
            }
        }
    }
    if (isGameInfo) {
        InfoGameIRLCompose(viewModel = viewModel, toggleGameInfo)
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun GameItem(gameIRL: GameIRL, viewModel: ListingViewModel, toggleGameInfo: () -> Unit) {
    Column (modifier = Modifier.padding(16.dp)) {
        Card (
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .height(30.dp)
                .clickable {
                    viewModel.currentGame = gameIRL
                    toggleGameInfo()
                }
        ) {
            Text(text = gameIRL.description, fontSize = 13.sp, textAlign = TextAlign.Start)
            val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm")
            Text(
                text = sdf.format(gameIRL.date),
                fontSize = 13.sp, textAlign = TextAlign.End
            )
        }

    }
}