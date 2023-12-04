package com.example.chessgo.frontend.irlMenu.searching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SearchingMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MapViewModel()
        val db = Firebase.firestore
        // ToDo something like that
        // viewModel.GetPoints()
        setContent {
            MapScreen(viewModel = viewModel)
        }
    }
}