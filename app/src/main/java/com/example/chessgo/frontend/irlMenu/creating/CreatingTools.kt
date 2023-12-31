package com.example.chessgo.frontend.irlMenu.creating

import com.example.chessgo.backend.irl.CreatingIRLManager
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.time.LocalTime

class CreatingTools {
    private val creatingIRLManager = CreatingIRLManager()

    fun createEvent(description : String = "", date : LocalDate, time : LocalTime, position : LatLng) {
        creatingIRLManager.addNewEventToFirestore(description, date, time, position)
    }
}