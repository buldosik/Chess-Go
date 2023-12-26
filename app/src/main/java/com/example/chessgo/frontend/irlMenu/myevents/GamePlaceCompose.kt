package com.example.chessgo.frontend.irlMenu.myevents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GamePlaceCompose(togglePlaceInfo: () -> Unit, viewModel: ListingViewModel) {
    val center = viewModel.currentGame.position
    val defaultCameraPosition = CameraPosition.fromLatLngZoom(center, 10f)
    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }
    val mapProperties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }

    Surface(Modifier.fillMaxSize()) {
        Box {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                googleMapOptionsFactory = {
                    GoogleMapOptions().mapId("DEMO_MAP_ID")
                },
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                onPOIClick = {},
                onMapClick = {}
            ) {
                Marker(
                    state = MarkerState(position = viewModel.currentGame.position),
                )
            }
            Button(modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
                onClick = { togglePlaceInfo() })
            {
                Text(text = "<-")
            }
        }
    }
}