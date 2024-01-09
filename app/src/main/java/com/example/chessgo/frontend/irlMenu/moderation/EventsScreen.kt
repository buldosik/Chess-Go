package com.example.chessgo.frontend.irlMenu.moderation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
import com.example.chessgo.backend.GameResult
import com.example.chessgo.frontend.irlMenu.myevents.EmptyItem
import com.example.chessgo.frontend.navigation.navigateToMainMenu
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(navController: NavHostController = rememberNavController()){
    val viewModel = remember { ModerationViewModel() }
    var isGameInfo by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.getAllGamesForModeration()
    }


    val toggleGameInfo: () -> Unit = {
        isGameInfo = !isGameInfo
    }

    val onGameClick: (index: Int) -> Unit = {
        coroutineScope.launch {
            val firstItem = listState.firstVisibleItemIndex
            if (firstItem == it - 2) {
                listState.animateScrollToItem(it - 1)
            }
            else if (
                (firstItem != it && firstItem + 1 != it) || it == viewModel.games.size - 2) {
                listState.animateScrollToItem(index = it + 1)
            }
            listState.animateScrollToItem(index = it)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                text = "List of Games to be moderated:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                state = listState
            ) {
                itemsIndexed(viewModel.games) {index, item ->
                    if (index != viewModel.games.size - 1)
                        GameItem(gameResult = item, index, viewModel, onGameClick)
                    else
                        EmptyItem()
                }
            }
        }
    }
}
@SuppressLint("SimpleDateFormat")
@Composable
fun GameItem(
    gameResult: GameResult,
    index: Int,
    viewModel: ModerationViewModel,
    onGameClick: (Int) -> Unit,
) {
    var isExpanded=  remember { mutableStateOf(false) }
    val hostImage = viewModel.hostImage.value
    val enemyImage = viewModel.enemyImage.value
    //val hostResult = viewModel.currentGame.resultByHost
    //val enemyResult = viewModel.currentGame.resultByEnemy
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 600, // Adjust the duration as needed
                    easing = LinearOutSlowInEasing
                )
            )
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 120.dp, max = if (isExpanded.value) 540.dp else 120.dp)
            .clickable {
                viewModel.currentGame = gameResult
                viewModel.getImageFromStorage(viewModel.currentGame.gid)
                if (!isExpanded.value) {
                    onGameClick(index)
                }
                isExpanded.value = !isExpanded.value
            }
            .background(MaterialTheme.colorScheme.secondary),
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = gameResult.date.toString(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start
            )

            AnimatedVisibility(visible = isExpanded.value) {
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxSize()
                        .background( MaterialTheme.colorScheme.onPrimary),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // user1 host
                    UserBlock(player = "player 1/ host", bitmap = hostImage, resultChosen = viewModel.currentGame.resultByHost, fraction = 0.5f, viewModel = viewModel, isExpanded = isExpanded)
                    //user2 enemy
                    UserBlock(player = "player 2/ enemy", bitmap = enemyImage, resultChosen = viewModel.currentGame.resultByEnemy, fraction = 1.0f,viewModel = viewModel, isExpanded = isExpanded)
                }
            }
        }
    }
}

@Composable
fun UserBlock(player: String, bitmap: Bitmap?, resultChosen: String, fraction: Float, viewModel: ModerationViewModel, isExpanded: MutableState<Boolean>
) {
    val imageBitmap: ImageBitmap? = bitmap?.asImageBitmap()
    var isZoomed by remember { mutableStateOf(false) }
    val defaultDrawable = painterResource(id = R.drawable.empty_photo)
    Row (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(fraction),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Your Image",
                modifier = Modifier
                    .widthIn(max = if (isZoomed) 270.dp else 100.dp)
                    .heightIn(max = if (isZoomed) 270.dp else 100.dp)
                    .clickable {
                        isZoomed = !isZoomed
                    }
            )
        } else {
            // If bitmap is null, use a default drawable or placeholder
            Image(
                painter = defaultDrawable,
                contentDescription = "Placeholder Image",
                modifier = Modifier
                    .widthIn(max = if (isZoomed) 270.dp else 100.dp)
                    .heightIn(max = if (isZoomed) 270.dp else 100.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = player,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            Text(
                text = resultChosen,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
            Button(
                onClick = {
                    viewModel.markResultAsResolved(viewModel.currentGame)
                    isExpanded.value = !isExpanded.value
                    viewModel.games.remove(viewModel.currentGame)
                    Thread.sleep(1_00)
                },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text ="Choose $player",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                )
            }
        }
    }
}