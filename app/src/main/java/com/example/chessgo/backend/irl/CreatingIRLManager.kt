package com.example.chessgo.backend.irl

import android.util.Log
import com.example.chessgo.backend.EventIRL
import com.example.chessgo.backend.GameIRL
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
        val newEvent = GameIRL(
            date = TimeConverter.localDateTimeToDate(date.atTime(time)),
            description = description,
            position = position,
        )
        firestore.collection("events")
            .add(newEvent)
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
            position = position
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

    fun getListOfDisabledDates(): List<LocalDate> {
        val firstDateInCalendar = LocalDate.of(1980, 1, 1)
        val today = LocalDate.now().minusDays(1)
        val listOfBlockedDates = mutableListOf<LocalDate>()

        var currentDate = firstDateInCalendar
        while (currentDate.isBefore(today) || currentDate.isEqual(today)) {
            listOfBlockedDates.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }

        return listOfBlockedDates
    }
}