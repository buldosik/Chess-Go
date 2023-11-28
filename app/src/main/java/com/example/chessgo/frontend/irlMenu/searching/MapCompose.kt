package com.example.chessgo.frontend.irlMenu.searching

import androidx.compose.ui.graphics.Color
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.random.Random
import androidx.compose.runtime.*
import com.example.chessgo.backend.EventIRL

private const val TAG = "AdvancedMarkersActivity"

// toRemove
private val center = LatLng(-18.000, -58.000)
private val defaultCameraPosition1 = CameraPosition.fromLatLngZoom(center, 2f)

@Composable
fun MapScreen(
    viewModel: MapViewModel
) {
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
        mutableFloatStateOf(0.0f)
    }

    // toRemove
    var id = 0f

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
                Marker(
                    state = MarkerState(position = point.position),
                    zIndex = point.gid,
                    onClick = {
                        Log.d(TAG, "Marker Clicked: ${it.zIndex}")
                        chosenMarker = it.zIndex
                        isInfoBoxVisible = true
                        true
                    }
                )
            }
        }
        // ToRemove
        // Button to add a new point
        Button(
            onClick = {
                // Handle button click to add a new point
                val newPoint = LatLng(
                    Random.nextDouble(-90.0, 90.0),
                    Random.nextDouble(-180.0, 180.0)
                )
                id++
                val eventIRL = EventIRL(
                    gid = id,
                    position = newPoint,
                )
                viewModel.points.add(eventIRL)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text("Add Point")
        }

        // InfoBox of events
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .align(Alignment.BottomCenter)
                .size(200.dp)
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
                InfoAboutEvent(chosenMarker)
            }
        }
    }
}

@Composable
fun InfoAboutEvent(chosenMarkerGID : Float) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        // ToDo add request to db depending on GID
        // ToDo make nice info about event
        // ToDo add button to apply to that event

        // example
        TextField(
            value = "Some name",
            onValueChange = { },
            label = { Text("Field 1") },
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
        )
    }
}