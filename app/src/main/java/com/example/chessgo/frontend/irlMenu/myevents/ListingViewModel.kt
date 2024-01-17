package com.example.chessgo.frontend.irlMenu.myevents

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.GeocoderUtils
import com.example.chessgo.backend.global.LoadDataCallback
import com.example.chessgo.backend.global.TimeConverter
import com.example.chessgo.backend.irl.ListingIRLManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import java.time.LocalDateTime

private const val TAG = "ListingViewModel"

class ListingViewModel: ViewModel() {
    val listingIRLManager = ListingIRLManager()
    val games = mutableStateListOf<GameIRL>()
    private val geocoder = GeocoderUtils()
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

    fun removeCurrentGame() {
        listingIRLManager.signOffGame(currentGame)
        games.remove(currentGame)
    }

    suspend fun getAddress(context: Context, callback: LoadDataCallback<String>) {
        delay(5000)
        geocoder.getAddressFromPoint(context, currentGame.position, callback)
    }
}