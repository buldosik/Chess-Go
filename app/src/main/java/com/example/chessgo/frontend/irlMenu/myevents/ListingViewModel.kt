package com.example.chessgo.frontend.irlMenu.myevents

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.TimeConverter
import com.example.chessgo.backend.irl.ListingIRLManager
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

private const val TAG = "ListingViewModel"

class ListingViewModel: ViewModel() {
    val listingIRLManager = ListingIRLManager()
    val games = mutableStateListOf<GameIRL>()
    lateinit var currentGame: GameIRL

    fun getGames() {
        Log.d(TAG, "GETTING GAMES")
        games.clear()
        listingIRLManager.getAllGames { gamesIRL : MutableList<GameIRL> ->
            gamesIRL.forEach {
                games.add(it)
            }
            games.add(GameIRL(
                position = LatLng(0.0, 0.0),
                date = TimeConverter.localDateTimeToDate(LocalDateTime.now())))
        }
    }
}