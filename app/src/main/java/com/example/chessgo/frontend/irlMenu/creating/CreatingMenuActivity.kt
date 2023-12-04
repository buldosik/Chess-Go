package com.example.chessgo.frontend.irlMenu.creating

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.backend.irl.CreatingIRLManager
import com.example.chessgo.frontend.mainmenu.MainMenuActivity
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalTime


class CreatingMenuActivity : ComponentActivity() {
    private val viewModel = CreatingViewModel()
    private val creatingIRLManager = CreatingIRLManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firestore = Firebase.firestore
        setContent {
            CreatingMenu(viewModel = viewModel, this)
        }
    }
    fun goToPlacePicker() {
        setContent {
            PlacePicker(viewModel = viewModel, this)
        }
    }
    fun goToMenu() {
        setContent {
            //PlacePicker(viewModel = viewModel)
            CreatingMenu(viewModel = viewModel, this)
        }
    }

    fun goToMainMenu() {
        val intent = Intent(applicationContext, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun createEvent(description : String = "", date : LocalDate, time : LocalTime, position : LatLng) {
        creatingIRLManager.addNewEventToFirestore(description, date, time, position)
    }
}