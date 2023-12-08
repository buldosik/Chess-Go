package com.example.chessgo.frontend.irlMenu.creating

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.time.LocalTime

class CreatingViewModel : ViewModel() {
    var description : String = ""
    var pickedDate : LocalDate = LocalDate.now()
    var pickedTime : LocalTime = LocalTime.now()
    var pickedPoint : LatLng = LatLng(0.0, 0.0)
}