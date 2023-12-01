package com.example.chessgo.frontend.irlMenu.creating

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.frontend.mainmenu.MainMenuActivity


class CreatingMenuActivity : ComponentActivity() {
    private val viewModel = CreatingViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ToDo something like that
        // viewModel.GetPoints()
        setContent {
            //PlacePicker(viewModel = viewModel)
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
}