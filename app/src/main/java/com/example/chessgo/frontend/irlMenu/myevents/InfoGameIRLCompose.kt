package com.example.chessgo.frontend.irlMenu.myevents

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat

private const val TAG = "InfoGameIrl"

@SuppressLint("SimpleDateFormat")
@Composable
fun InfoGameIRLCompose(
    viewModel: ListingViewModel,
    toggleGameInfo: () -> Unit
) {
    var isPlaceInfo by remember { mutableStateOf(false) }

    val togglePlaceInfo: () -> Unit = {
        isPlaceInfo = !isPlaceInfo
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Description: \n" + viewModel.currentGame.description)
            val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm")
            Text(text = "Date: " + sdf.format(viewModel.currentGame.date))
            Text(text = "GID: " + viewModel.currentGame.gid)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Place", textAlign = TextAlign.Start)
                Spacer(modifier = Modifier.padding(20.dp))
                Button(onClick = {
                    togglePlaceInfo()
                }) {
                    Text(text = "Map")
                }
            }
            Button(modifier = Modifier.padding(top = 10.dp), onClick = {
                viewModel.listingIRLManager.signOffGame(viewModel.currentGame)
                Log.d(TAG, viewModel.games.toString())
                Thread.sleep(1_00)
                toggleGameInfo() /* Go to my events menu */
            }) {
                Text(text = "Sign off")
            }
            Button(
                modifier = Modifier.padding(top = 10.dp),
                onClick = { toggleGameInfo() /* Go to my events menu */ }) {
                Text(text = "Go to list")
            }
        }
    }
    if (isPlaceInfo){
        GamePlaceCompose(togglePlaceInfo, viewModel)
    }
}