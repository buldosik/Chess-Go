package com.example.chessgo.backend

import com.google.android.gms.maps.model.LatLng

/**
 * Data class representing real-world events with associated information, including unique identifiers and geographic positions.
 *
 * @param gid Unique identifier for the event.
 * @param position Geographic position of the event specified as a LatLng object.
 */
data class EventIRL(
    val gid: Float,
    val position: LatLng,
)
