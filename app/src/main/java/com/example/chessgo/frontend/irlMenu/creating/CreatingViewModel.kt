package com.example.chessgo.frontend.irlMenu.creating

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class CreatingViewModel : ViewModel() {
    var lastPoint : LatLng = LatLng(0.0, 0.0)
}