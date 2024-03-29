package com.example.chessgo.backend.irl

import android.util.Log
import com.example.chessgo.backend.EventIRL
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.backend.global.TimeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalTime

private const val TAG = "CreatingManager"

class CreatingIRLManager {
    private val firestore = Firebase.firestore
    fun addNewEventToFirestore(description : String = "", date : LocalDate, time : LocalTime, position : LatLng) {
        val newGame = GameIRL(
            date = TimeConverter.localDateTimeToDate(date.atTime(time)),
            description = description,
            position = position,
            host = ClientManager.getClient().uid
        )
        firestore.collection("events")
            .add(newGame)
            .addOnSuccessListener { documentRef ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentRef.id}")
                addNewEventToMap(position, documentRef.id)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                // ToDo throw error
            }
    }
    private fun addNewEventToMap(position: LatLng, gid: String) {
        val newEvent = EventIRL(
            position = position,
            hostUID = ClientManager.getClient().uid
        )
        firestore.collection("events_on_map").document(gid)
            .set(newEvent)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot written to events_on_map}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                // ToDo throw error
            }
    }
}