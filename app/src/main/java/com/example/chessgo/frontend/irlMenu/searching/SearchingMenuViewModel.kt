package com.example.chessgo.frontend.irlMenu.searching

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.EventIRL
import com.example.chessgo.backend.irl.SearchingIRLManager

private const val TAG = "SearchingMenuViewModel"

class SearchingMenuViewModel : ViewModel() {
    val searchingIRLManager = SearchingIRLManager()
    val points = mutableStateListOf<EventIRL>()
    fun getPoints() {
        searchingIRLManager.getAllEvents { eventIRLS: MutableList<EventIRL> ->
            eventIRLS.forEach {
                Log.d(TAG, "Marker data: ${it.position}")
                points.add(it)
            }
            Log.d(TAG, "Size: ${points.size}")
        }
    }

}

