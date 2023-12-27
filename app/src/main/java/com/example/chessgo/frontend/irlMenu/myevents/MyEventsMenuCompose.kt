package com.example.chessgo.frontend.irlMenu.myevents

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.chessgo.backend.GameIRL
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@Composable
fun MyEventsScreen(navController: NavHostController) {
    val viewModel = remember { ListingViewModel() }
    var isGameInfo by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    viewModel.getGames()

    val toggleGameInfo: () -> Unit = {
        isGameInfo = !isGameInfo
    }

    val onGameClick: (index: Int) -> Unit = {
        coroutineScope.launch {
            listState.animateScrollToItem(index = it)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your games:",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                state = listState
            ) {
                itemsIndexed(viewModel.games) {index, item ->
                    GameItem(gameIRL = item, index, viewModel, toggleGameInfo, onGameClick)
                }
            }
        }

        if (isGameInfo) {
            InfoGameIRLCompose(viewModel = viewModel, toggleGameInfo)
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun GameItem(
    gameIRL: GameIRL,
    index: Int,
    viewModel: ListingViewModel,
    toggleGameInfo: () -> Unit,
    onGameClick: (Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }


    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 400, // Adjust the duration as needed
                    easing = LinearOutSlowInEasing
                )
            )
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 120.dp, max = if (isExpanded) 600.dp else 120.dp)
            .clickable {
                viewModel.currentGame = gameIRL
                isExpanded = !isExpanded
                if (isExpanded) {
                    onGameClick(index)
                }
//                toggleGameInfo()
            },
        color = Color(0xFFFAE6FA),
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = gameIRL.description,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )

            val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm")
            Text(
                text = sdf.format(gameIRL.date),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
            AnimatedVisibility(visible = isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Expanded Content",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
