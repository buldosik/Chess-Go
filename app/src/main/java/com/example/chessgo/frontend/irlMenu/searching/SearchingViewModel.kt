package com.example.chessgo.frontend.irlMenu.searching

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.EventIRL
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.backend.global.GeocoderUtils
import com.example.chessgo.backend.global.LoadDataCallback
import com.example.chessgo.backend.irl.SearchingIRLManager
import com.google.android.gms.maps.model.LatLng

private const val TAG = "SearchingMenuViewModel"

class SearchingViewModel: ViewModel() {
    private val searchingIRLManager = SearchingIRLManager()
    private val geocoderUtils = GeocoderUtils()
    val points = mutableStateListOf<EventIRL>()
    fun getPoints() {
        Log.d(TAG, "GETTING EVENTS")
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

    fun getAddressFromPoint(context: Context, position: LatLng, loadDataCallback: LoadDataCallback<String>) {
        geocoderUtils.getAddressFromPoint(context, position, loadDataCallback)
    }

}

