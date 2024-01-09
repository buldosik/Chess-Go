package com.example.chessgo.frontend.irlMenu.moderation

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.GameResult
import com.example.chessgo.backend.irl.ListingIRLManager
import com.example.chessgo.backend.registration.Results

private const val TAG = "ModerationViewModel"
class ModerationViewModel : ViewModel(){
    private  val listingIRLManager = ListingIRLManager()
    val games = mutableStateListOf<GameResult>()
    val hostImage = mutableStateOf<Bitmap?>(null)
    val enemyImage = mutableStateOf<Bitmap?>(null)
    lateinit var currentGame: GameResult
    fun getAllGamesForModeration(){
        games.clear()
        listingIRLManager.getAllGamesForModeration { gamesResults : MutableList<GameResult> ->

            gamesResults.forEach {
                games.add(it)
            }

            /*
            games.add(GameIRL(
            position = LatLng(0.0, 0.0),
            date = TimeConverter.localDateTimeToDate(LocalDateTime.now())))
            */
        }
    }
    fun getImageFromStorage(eventId: String){
        listingIRLManager.getHostPath(eventId)?.let {
            listingIRLManager.getImageFromStorage(it) { result ->
                when(result){
                    is Results.Success -> {
                        val bitmap = result.data
                        if(bitmap!=null){
                            hostImage.value = bitmap
                            Log.d(TAG, "Downloaded  host image as Bitmap.")
                        }
                    }

                    is Results.Failure -> {
                        val path = listingIRLManager.getHostPath(eventId)
                        Log.d(TAG, "Host path $path")
                    }
                }
            }
        }
        listingIRLManager.getEnemyPath(eventId)?.let {
            listingIRLManager.getImageFromStorage(it) { result ->
                when(result){
                    is Results.Success -> {
                        val bitmap = result.data
                        if(bitmap!=null){
                            enemyImage.value = bitmap
                            Log.d(TAG, "Downloaded enemy image as Bitmap.")
                        }
                    }

                    is Results.Failure -> {
                        Log.d(TAG, "Failed to get Image from storage")
                    }
                }
            }
        }
    }

    fun markResultAsResolved(gameResult: GameResult){
        listingIRLManager.markResultAsResolved(gameResult)
    }

}