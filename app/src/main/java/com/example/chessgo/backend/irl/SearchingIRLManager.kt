package com.example.chessgo.backend.irl

import android.util.Log
import com.example.chessgo.backend.EventIRL
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.ClientManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "SearchingIRLManager"

class SearchingIRLManager {
    private val firestore = Firebase.firestore
    fun getAllEvents(callback: (MutableList<EventIRL>) -> Unit) {
        firestore.collection("events_on_map")
            .get()
            .addOnSuccessListener { documents ->
                val eventList = mutableListOf<EventIRL>()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    var event = EventIRL.toEventIRL(document)
                    Log.d(TAG, "Converted ${event.position}")
                    eventList.add(event)

                }
                callback(eventList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                callback(mutableListOf<EventIRL>())
            }
    }
    fun getInfoAboutEvent(gid: String, callback: (GameIRL?) -> Unit) {
        firestore.collection("events").document(gid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Document exists, extract data and invoke the callback
                    val gameIRL = GameIRL.toGameIRL(documentSnapshot)
                    // Invoke the callback
                    callback(gameIRL)
                } else {
                    // Document does not exist, invoke the callback with a default value
                    Log.w(TAG, "Document do not exist")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                callback(null)
            }
    }
    fun applyToEvent(gid: String, callback: () -> Unit) {
        firestore.collection("events").document(gid)
            .update("enemy", ClientManager.getClient().uid)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener {
                    e -> Log.w(TAG, "Error updating document", e)
                    callback()
            }
    }
}