package com.example.chessgo.frontend.irlMenu.searching

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.backend.EventIRL
import com.example.chessgo.backend.irl.SearchingIRLManager

private const val TAG = "SearchingMenuActivity"

class SearchingMenuActivity : ComponentActivity() {
    private val viewModel = MapViewModel()
    private val searchingIRLManager = SearchingIRLManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ToDo ReDoIt to not parse all points
        searchingIRLManager.getAllEvents { eventIRLS: MutableList<EventIRL> ->
            eventIRLS.forEach {
                Log.d(TAG, "Marker data: ${it.position}")
                viewModel.points.add(it)
            }
            Log.d(TAG, "Size: ${viewModel.points.size}")
        }
        Thread.sleep(5000)
        setContent {
            MapScreen(viewModel = viewModel)
        }
    }
}