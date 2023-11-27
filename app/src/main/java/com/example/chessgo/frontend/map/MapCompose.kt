package com.example.chessgo.frontend.map

import android.graphics.Color
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PinConfig
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.random.Random

private const val TAG = "AdvancedMarkersActivity"

private val santiago = LatLng(-33.4489, -70.6693)
private val bogota = LatLng(-4.7110, -74.0721)
private val lima = LatLng(-12.0464, -77.0428)
private val salvador = LatLng(-12.9777, -38.5016)
private val center = LatLng(-18.000, -58.000)
private val defaultCameraPosition1 = CameraPosition.fromLatLngZoom(center, 2f)


val singapore = LatLng(1.35, 103.87)

@Composable
fun MapScreen(
    viewModel: MapViewModel
) {
    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition1
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }

    var id = 0f

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            googleMapOptionsFactory = {
                GoogleMapOptions().mapId("DEMO_MAP_ID")
            },
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onPOIClick = {
                Log.d(TAG, "POI clicked: ${it.latLng}")
            },
            onMapClick = {
                Log.d(TAG, "POI clicked: $it")
            }
        ) {
            viewModel.points.forEach {
                Marker(
                    state = MarkerState(position = it.position),
                    title = it.title,
                    snippet = it.snippet,
                    zIndex = it.id
                )
            }
            /* val pinConfig = PinConfig.builder()
                .setBackgroundColor(Color.MAGENTA)
                .setBorderColor(Color.WHITE)
                .build()

                AdvancedMarker(
                state = MarkerState(LatLng(-33.4489, -70.6693)),
                collisionBehavior = 1,
                pinConfig = pinConfig,
                title="Marker 1"
            )*/
        }
        // Button to add a new point
        Button(
            onClick = {
                // Handle button click to add a new point
                val newPoint = LatLng(
                    Random.nextDouble(-90.0, 90.0),
                    Random.nextDouble(-180.0, 180.0)
                )
                val eventIRL = EventIRL(
                    id = id,
                    position = newPoint,
                )
                viewModel.points.add(eventIRL)
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("Add Point")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Prev() {
    MapScreen(
        viewModel = MapViewModel()
    )
}