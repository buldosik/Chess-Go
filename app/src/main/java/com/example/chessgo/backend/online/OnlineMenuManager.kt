package com.example.chessgo.backend.online

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

private const val TAG = "OnlineGameManager"

class OnlineMenuManager {

    private val auth = Firebase.auth
    private val db = Firebase.database
    private val functions = Firebase.functions

    fun startMatchmaking(callback: (String) -> Unit) {
        functions.getHttpsCallable("matchmaking")
            .call()

        val lobbyFoundRef = db.getReference("lobby_found/${auth.uid}")

        val postListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val lobbyId = snapshot.value.toString()
                if (lobbyId == null || lobbyId == "" || lobbyId == "Searching" )
                    return
                if(lobbyId.length > 3) {
                    lobbyFoundRef.removeEventListener(this)
                    lobbyFoundRef.removeValue()
                    callback(lobbyId)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG,"loadPost:onCancelled")
            }
        }

        lobbyFoundRef.addValueEventListener(postListener)
    }
}