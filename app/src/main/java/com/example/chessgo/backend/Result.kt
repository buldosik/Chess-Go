package com.example.chessgo.backend

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

class Result (
    val gid : String = "",
    val date : Date,
    val host : String = "",
    val enemy : String = "",
    val resultByHost : String = "",
    val imageByHost : String = "",
    val resultByEnemy : String = "",
    val imageByEnemy : String = "",
    val isModerationNeeded : Boolean,
    val approvesNum : Int
) {
    companion object {
        fun toResult(document: DocumentSnapshot) : Result {
            val gui = document.id
            val date = document.getDate("date")
            val host = document.get("host") as String
            val enemy = document.get("enemy") as String
            val resultByHost = document.get("resultByHost") as String
            val imageByHost = document.get("imageByHost") as String
            val resultByEnemy = document.get("resultByHost") as String
            val imageByEnemy = document.get("imageByEnemy") as String
            val isModerationNeeded = document.get("isModerationNeeded") as Boolean
            val approvesNum = document.get("approvesNum") as Int

            return Result(
                gui,
                date!!,
                host,
                enemy,
                resultByHost,
                imageByHost,
                resultByEnemy,
                imageByEnemy,
                isModerationNeeded,
                approvesNum
            )
        }
    }
}