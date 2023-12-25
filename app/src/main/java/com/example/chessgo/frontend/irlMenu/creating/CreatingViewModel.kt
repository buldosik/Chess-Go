package com.example.chessgo.frontend.irlMenu.creating

import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.irl.CreatingIRLManager
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.time.LocalTime

class CreatingViewModel : ViewModel() {
    private val creatingIRLManager = CreatingIRLManager()
    var description : String = ""
    var pickedDate : LocalDate = LocalDate.now()
    var pickedTime : LocalTime = LocalTime.now()
    var pickedPoint : LatLng = LatLng(0.0, 0.0)

    fun createEvent(description : String = "", date : LocalDate, time : LocalTime, position : LatLng) {
        creatingIRLManager.addNewEventToFirestore(description, date, time, position)
    }
    fun createEvent() {
        creatingIRLManager.addNewEventToFirestore(
            description,
            pickedDate,
            pickedTime,
            pickedPoint)
    }
}