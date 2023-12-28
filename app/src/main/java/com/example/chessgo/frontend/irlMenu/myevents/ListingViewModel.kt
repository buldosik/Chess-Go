package com.example.chessgo.frontend.irlMenu.myevents

import androidx.compose.runtime.mutableStateListOf
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.TimeConverter
import com.example.chessgo.backend.irl.ListingIRLManager
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ListingViewModel {
    val listingIRLManager = ListingIRLManager()
    val games = mutableStateListOf<GameIRL>()
    lateinit var currentGame: GameIRL

    fun getGames() {
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