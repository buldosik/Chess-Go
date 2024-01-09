package com.example.chessgo.frontend.irlMenu.creating

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.chessgo.backend.global.GeocoderUtils
import com.example.chessgo.backend.global.LoadDataCallback
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
    onPlacePickerVisibilityChanged: (LatLng) -> Unit,
    togglePlacePicker: () -> Unit
) {
    val context = LocalContext.current
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

    var isAddressExists by remember {
        mutableStateOf(false)
    }
    var currentAddress by remember {
        mutableStateOf("")
    }

    val loadDataCallback = object: LoadDataCallback<String> {
        override fun onDataLoaded(response: String) {
            currentAddress = response
            isAddressExists = true
        }

        override fun onDataNotAvailable(errorCode: Int, reasonMsg: String) {
            Log.d(TAG, "Error code: $errorCode, Message : $reasonMsg")
            isAddressExists = false
        }
    }

    val geocoderUtils = GeocoderUtils()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 48.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Adding map on full field
        GoogleMap(
            modifier = Modifier
                .matchParentSize(),
            googleMapOptionsFactory = {
                GoogleMapOptions().mapId("PICKING_POINT_MAP_ID")
            },
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onPOIClick = { poi ->
                Log.d(TAG, "POI clicked: ${poi.name}")
                currentPoint = poi.latLng
                geocoderUtils.getAddressFromPoint(context, currentPoint, loadDataCallback)
                isPointVisible = true
            },
            onMapClick = { mapLatLng ->
                Log.d(TAG, "Map clicked: $mapLatLng")
                currentPoint = mapLatLng
                geocoderUtils.getAddressFromPoint(context, currentPoint, loadDataCallback)
                isPointVisible = true
            }
        ) {
            if(isPointVisible){
                Marker(
                    state = MarkerState(position = currentPoint)
                )
            }
        }
        if(isAddressExists) {
            BoxInfo(
                text = currentAddress,
                onCancel = {
                    isPointVisible = false
                    togglePlacePicker()
               },
                onConfirm = {
                    onPlacePickerVisibilityChanged(currentPoint)
                    togglePlacePicker()
                },
            )
        }
        else {
            BoxInfo(
                text = currentPoint.toString(),
                onCancel = {
                    isPointVisible = false
                    togglePlacePicker()
                },
                onConfirm = {
                    onPlacePickerVisibilityChanged(currentPoint)
                    togglePlacePicker()
                },
            )
        }
    }
}

@Composable
fun BoxInfo(text: String, onCancel: () -> Unit, onConfirm: () -> Unit) {
    Surface(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .weight(0.25f)
                    .width(50.dp)
                    .background(Color.Transparent),
                onClick = { onCancel() },
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                modifier = Modifier.weight(1f).padding(4.dp),
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(
                modifier = Modifier
                    .weight(0.25f)
                    .width(50.dp)
                    .background(Color.Transparent),
                onClick = { onConfirm() },
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}