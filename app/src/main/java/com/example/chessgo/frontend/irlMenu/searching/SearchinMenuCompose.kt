package com.example.chessgo.frontend.irlMenu.searching

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.frontend.navigation.navigateToMainMenu
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Date

private const val TAG = "AdvancedMarkersActivity"

// toRemove
private val center = LatLng(51.0,17.0)
private val defaultCameraPosition1 = CameraPosition.fromLatLngZoom(center, 2f)

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SearchingScreen(navController: NavHostController) {
    val viewModel = remember { SearchingMenuViewModel() }
    viewModel.getPoints()

    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition1
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var isInfoBoxVisible by remember {
        mutableStateOf(false)
    }
    var chosenMarker by remember {
        mutableStateOf("")
    }

    Box(Modifier.fillMaxSize()) {
        // Adding map on full field
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            googleMapOptionsFactory = {
                GoogleMapOptions().mapId("DEMO_MAP_ID")
            },
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onPOIClick = {
                Log.d(TAG, "SOMETIME")
                Log.d(TAG, "POI clicked: ${it.name}")
                isInfoBoxVisible = false
            },
            onMapClick = {
                Log.d(TAG, "POI clicked: $it")
                isInfoBoxVisible = false
            }
        ) {
            // Adding all markers to map
            viewModel.points.forEach { point ->
                Log.d(TAG, "Marker Spawned: ${point.position}")
                Marker(
                    state = MarkerState(position = point.position),
                    title = point.gui,
                    onClick = {
                        Log.d(TAG, "Marker Clicked: ${it.title}")
                        chosenMarker = it.title.toString()
                        isInfoBoxVisible = true
                        true
                    }
                )
            }
        }

        // InfoBox of events
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .align(Alignment.BottomCenter)
                .size(450.dp, 300.dp)
        ) {
            AnimatedVisibility(
                visible = isInfoBoxVisible,
                enter = fadeIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    initialAlpha = 0.4f
                ),
                exit = fadeOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 250)
                )
            ) {
                InfoAboutEvent(chosenMarker, viewModel, navController)
            }
        }
    }
}

@Composable
fun InfoAboutEvent(chosenMarkerGID: String, viewModel: SearchingMenuViewModel, navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        var gameIrl by remember { mutableStateOf(GameIRL(
            position = LatLng(0.0, 0.0),
            date = Date(),
        )) }

        viewModel.searchingIRLManager.getInfoAboutEvent(chosenMarkerGID) {
            if (it != null) {
                gameIrl = it
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            OutlinedTextField(
                value = gameIrl.gid,
                onValueChange = {  },
                label = { Text("GID") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 1.dp),
                enabled = false
            )
            OutlinedTextField(
                value = gameIrl.description,
                onValueChange = {  },
                label = { Text("Description") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 1.dp),
                enabled = false
            )
            OutlinedTextField(
                value = gameIrl.date.toString(), // assuming date is a Date or a String
                onValueChange = { },
                label = { Text("Date") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 1.dp),
                enabled = false
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Assuming LatLng is a data class with two Double properties: latitude and longitude
                OutlinedTextField(
                    value = gameIrl.position.latitude.toString(),
                    onValueChange = { /* update view model or handle change */ },
                    label = { Text("Latitude") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 1.dp),
                    enabled = false
                )

                OutlinedTextField(
                    value = gameIrl.position.longitude.toString(),
                    onValueChange = { /* update view model or handle change */ },
                    label = { Text("Longitude") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 1.dp),
                    enabled = false
                )

            }
            Button(
                onClick = {
                    // ToDo apply to event
                    navController.navigateToMainMenu()
                },
                modifier = Modifier
                    .height(12.dp)
            ) {
                Text("+")
            }
        }
    }
}