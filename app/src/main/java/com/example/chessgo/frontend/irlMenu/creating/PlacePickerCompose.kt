package com.example.chessgo.frontend.irlMenu.creating

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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

private const val TAG = "PlacePickerCompose"

// toRemove
private val center = LatLng(51.0,17.0)
private val defaultCameraPosition1 = CameraPosition.fromLatLngZoom(center, 2f)

@Composable
fun PlacePicker(
    onPlacePickerVisibilityChanged: (LatLng) -> Unit
) {
    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition1
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var isPointVisible by remember {
        mutableStateOf(false)
    }
    var currentPoint by remember {
        mutableStateOf(LatLng(0.0, 0.0))
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
            onPOIClick = { poi ->
                Log.d(TAG, "POI clicked: ${poi.name}")
                currentPoint = poi.latLng
                isPointVisible = true
            },
            onMapClick = { mapLatLng ->
                Log.d(TAG, "Map clicked: $mapLatLng")
                currentPoint = mapLatLng
                isPointVisible = true
            }
        ) {
            if(isPointVisible){
                Marker(
                    state = MarkerState(position = currentPoint)
                )
            }
        }
        // Button to confirm point
        Button(
            onClick = {
                onPlacePickerVisibilityChanged(currentPoint)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("Confirm")
        }
        Button(
            onClick = {
                isPointVisible = false
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text("Clear")
        }
    }
}