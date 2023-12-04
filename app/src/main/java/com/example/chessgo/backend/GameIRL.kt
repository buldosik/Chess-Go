package com.example.chessgo.backend

import com.google.android.gms.maps.model.LatLng
import java.util.Date

class GameIRL(
    gid: String = "",
    date: Date,
    host: String = "",
    enemy: String = "",
    typeOfGame: String = "",
    result: String = "",
    status: String = "",
    val position: LatLng,
    val description: String = "",
    val image: String? = null,
) : Game(gid, date, host, enemy, typeOfGame, result, status) {

    // TODO implementation of IrlGame class

}