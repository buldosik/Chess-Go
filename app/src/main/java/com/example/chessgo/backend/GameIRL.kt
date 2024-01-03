package com.example.chessgo.backend

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
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
    companion object {
        fun toGameIRL(document: DocumentSnapshot) : GameIRL {
            val gui = document.id
            val description = document.get("description")
            val date = document.getDate("date")

            val host = document.get("host") as String
            val enemy = document.get("enemy") as String
            val dataMap = document.get("position") as? Map<*, *>
            val latitude = (dataMap?.get("latitude") as? Double) ?: 0.0
            val longitude = (dataMap?.get("longitude") as? Double) ?: 0.0

            val position = LatLng(latitude, longitude)

            return GameIRL(gid = gui, position = position, date = date!!, host = host, enemy = enemy, description = description.toString())
        }
    }

}