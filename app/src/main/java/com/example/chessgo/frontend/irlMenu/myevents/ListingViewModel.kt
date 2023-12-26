package com.example.chessgo.frontend.irlMenu.myevents

import androidx.compose.runtime.mutableStateListOf
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.irl.ListingIRLManager

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
        }
    }
}