package com.example.chessgo.frontend.map

import com.google.android.gms.maps.model.LatLng

data class EventIRL(
    val id: Float,
    val position: LatLng,
    val title: String = "No title",
    val snippet: String = "No snippet",
)
