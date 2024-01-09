package com.example.chessgo.backend.irl

import android.util.Log
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.ClientManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "CreatingManager"

class ListingIRLManager {
    private val firestore = Firebase.firestore
    fun getAllGames(callback: (MutableList<GameIRL>) -> Unit) {
        firestore.collection("events")
            .get()
            .addOnSuccessListener { documents ->
                val gamesList = mutableListOf<GameIRL>()
                val uid = ClientManager.getClient().uid
                Log.d(TAG, "CLIENT: $uid")
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val event = GameIRL.toGameIRL(document)
                    Log.d(TAG, "Description: ${event.description}, host: ${event.host}, enemy: ${event.enemy}")
                    if (event.host == uid || event.enemy == uid) {
                        gamesList.add(event)
                    }
                }
                callback(gamesList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                callback(mutableListOf<GameIRL>())
            }
    }

    fun signOffGame(gameIRL: GameIRL) {
        val document = firestore.collection("events").document(gameIRL.gid)
        val map_document = firestore.collection("events_on_map").document(gameIRL.gid)

        map_document.get()
            .addOnFailureListener() {
                //TODO
            }

        document.get()
            .addOnSuccessListener() {
                val game = GameIRL.toGameIRL(it)
                val uid = ClientManager.getClient().uid
                if (game.host == "" || game.enemy == "") {
                    document.delete()
                    map_document.delete()
                } else {
                    if (game.host == uid) {
                        document.delete()
                        map_document.delete()
                    } else {
                        document.update("enemy", "")
                        map_document.update("full", false)
                    }
                }
            }
            .addOnFailureListener() {
                // TODO
            }


    }
}