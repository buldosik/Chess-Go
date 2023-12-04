package com.example.chessgo.backend.irl

import android.util.Log
import com.example.chessgo.backend.EventIRL
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
}