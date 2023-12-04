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
    val position: LatLng = LatLng(0.0, 0.0),
) {
    companion object {
        fun toEventIRL(document: QueryDocumentSnapshot) : EventIRL {
            val gui = document.id
            val dataMap = document.get("position") as? Map<*, *>
            val latitude = (dataMap?.get("latitude") as? Double) ?: 0.0
            val longitude = (dataMap?.get("longitude") as? Double) ?: 0.0

            val position = LatLng(latitude, longitude)

            return EventIRL(gui = gui, position = position,)
        }
    }
}
