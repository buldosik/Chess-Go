package com.example.chessgo.frontend.irlMenu.searching

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import com.example.chessgo.backend.EventIRL
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.backend.irl.SearchingIRLManager

private const val TAG = "SearchingMenuViewModel"

class SearchingTools {
    private val searchingIRLManager = SearchingIRLManager()
    val points = mutableStateListOf<EventIRL>()
    fun getPoints() {
        val clientInfo = ClientManager.getClient()
        searchingIRLManager.getAllEvents { eventIRLS: MutableList<EventIRL> ->
            eventIRLS.forEach {
                Log.d(TAG, "Marker data: ${it.gui}")
                Log.d(TAG, "Marker data: ${it.position}")
                Log.d(TAG, "Marker data: ${clientInfo.uid} --- ${it.hostUID}")
                if(clientInfo.uid == it.hostUID) {
                    return@forEach
                }
                if(it.isFull){
                    return@forEach
                }
                points.add(it)
            }
            Log.d(TAG, "Size: ${points.size}")
        }
    }
    fun getInfoAboutPoint(gid: String, callback: (GameIRL?) -> Unit) {
        searchingIRLManager.getInfoAboutEvent(gid, callback)
    }

    fun addEnemyToEvent(gid: String, context: Context) {
        searchingIRLManager.applyToEvent(gid) {
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

