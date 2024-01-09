package com.example.chessgo.backend

import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

class GameResult (
    val rid : String = "",
    val gid : String = "",
    val date : Date,
    val host : String = "",
    val enemy : String = "",
    val status : String = "",
    var resultByHost : String = "",
    var imageByHost : String = "",
    var resultByEnemy : String = "",
    var imageByEnemy : String = "",
    var moderationNeeded : Boolean = false,
    val approvesNum : Long = 0
) {
    companion object {
        fun toResult(document: DocumentSnapshot) : GameResult {
            val rid = document.id
            val gid = document.getString("gid") ?: ""
            val date = document.getDate("date")
            val host = document.getString("host") ?: ""
            val enemy = document.getString("enemy") ?: ""
            val resultByHost = document.getString("resultByHost") ?: ""
            val imageByHost = document.getString("imageByHost") ?: ""
            val resultByEnemy = document.getString("resultByEnemy") ?: ""
            val imageByEnemy = document.getString("imageByEnemy") ?: ""
            val isModerationNeeded = document.getBoolean("moderationNeeded") ?: false
            val approvesNum = document.getLong("approvesNum") ?: 0L


            return GameResult(
                rid = rid,
                gid = gid,
                date = date!!,
                host = host,
                enemy = enemy,
                resultByHost =resultByHost,
                imageByHost = imageByHost,
                resultByEnemy = resultByEnemy,
                imageByEnemy = imageByEnemy,
                moderationNeeded = isModerationNeeded,
                approvesNum = approvesNum
            )
        }
    }
}