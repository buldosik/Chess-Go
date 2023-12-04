package com.example.chessgo.frontend.irlMenu.searching

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.EventIRL

class MapViewModel : ViewModel() {
    val points = mutableStateListOf<EventIRL>()
}