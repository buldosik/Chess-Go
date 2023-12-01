package com.example.chessgo.frontend.irlMenu.searching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class SearchingMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MapViewModel()
        // ToDo something like that
        // viewModel.GetPoints()
        setContent {
            MapScreen(viewModel = viewModel)
        }
    }
}