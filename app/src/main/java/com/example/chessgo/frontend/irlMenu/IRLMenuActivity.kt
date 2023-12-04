package com.example.chessgo.frontend.irlMenu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.frontend.irlMenu.creating.CreatingMenuActivity
import com.example.chessgo.frontend.irlMenu.searching.SearchingMenuActivity
import com.example.chessgo.ui.theme.ChessgoTheme

class IRLMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessgoTheme {
                // A surface container using the 'background' color from the theme
                IRLMenu(this)
            }
        }
    }
    fun goToCreating() {
        val intent = Intent(applicationContext, CreatingMenuActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToSearching() {
        val intent = Intent(applicationContext, SearchingMenuActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToMyEvents() {
        // ToDo Future Updates
    }
}