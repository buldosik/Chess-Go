package com.example.chessgo.backend

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.QueryDocumentSnapshot

/**
 * Data class representing real-world events with associated information, including unique identifiers and geographic positions.
 *
 * @param gid Unique identifier for the event.
 * @param position Geographic position of the event specified as a LatLng object.
 */
data class EventIRL(
    val gui: String = "",
    var isFull: Boolean = false,
    val hostUID: String = "",
    val position: LatLng = LatLng(0.0, 0.0),
) {
    companion object {
        fun toEventIRL(document: QueryDocumentSnapshot) : EventIRL {
            val gui = document.id
            val isFull = document.get("isFull") as? Boolean ?: false
            val hostUID = document.get("hostUID") as? String ?: ""
            val dataMap = document.get("position") as? Map<*, *>
            val latitude = (dataMap?.get("latitude") as? Double) ?: 0.0
            val longitude = (dataMap?.get("longitude") as? Double) ?: 0.0

            val position = LatLng(latitude, longitude)

            return EventIRL(gui = gui, isFull = isFull, hostUID = hostUID, position = position)
        }
    }
}
