package com.example.chessgo.frontend.irlMenu.searching

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private const val TAG = "AdvancedMarkersActivity"

// toRemove
private val center = LatLng(51.0,17.0)
private val defaultCameraPosition1 = CameraPosition.fromLatLngZoom(center, 2f)

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SearchingScreen(navController: NavHostController) {
    val searchingTools = remember { SearchingTools() }
    searchingTools.getPoints()

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp, top = 24.dp),
        //color = MaterialTheme.colorScheme.background
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            googleMapOptionsFactory = {
                GoogleMapOptions().mapId("SEARCHING_MAP_ID")
            },
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onPOIClick = {
                Log.d(TAG, "POI clicked: ${it.name}")
                isInfoBoxVisible = false
            },
            onMapClick = {
                Log.d(TAG, "Map clicked: $it")
                isInfoBoxVisible = false
            }
        ) {
            // Adding all markers to map
            searchingTools.points.forEach { point ->
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

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = isInfoBoxVisible,
            enter = fadeIn (),
            exit = fadeOut ()
        ) {
            Surface(
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                shape = MaterialTheme.shapes.small,
                color = Color.Transparent
            ) {
                EventInfoBox(chosenMarker, searchingTools, navController)
            }
        }
    }
}

