package com.example.chessgo.frontend.map

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    //val points = mutableStateListOf<LatLng>()

    val points = mutableStateListOf<EventIRL>()
}