package com.example.chessgo.backend.irl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.GameResult
import com.example.chessgo.backend.global.ClientManager
import com.example.chessgo.backend.registration.Results
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

private const val TAG = "CreatingManager"

class ListingIRLManager {
    private val firestore = Firebase.firestore
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    fun getAllGames(callback: (MutableList<GameIRL>) -> Unit) {
        firestore.collection("events")
            .get()
            .addOnSuccessListener { documents ->
                val gamesList = mutableListOf<GameIRL>()
                val uid = ClientManager.getClient().uid
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val event = GameIRL.toGameIRL(document)
                    Log.d(TAG, "Description: ${event.description}, host: ${event.host}, enemy: ${event.enemy}")
                    if (event.host == uid || event.enemy == uid) {
                        gamesList.add(event)
                    }
                }
                callback(gamesList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                callback(mutableListOf<GameIRL>())
            }
    }
    fun getAllGamesForModeration(callback: (MutableList<GameResult>) -> Unit) {
        firestore.collection("results")
            .get()
            .addOnSuccessListener { documents ->
                val gamesList = mutableListOf<GameResult>()
                //val uid = ClientManager.getClient().uid
                //Log.d(TAG, uid)
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val event = GameResult.toResult(document)

                    Log.d(TAG, "PathToImageHost: ${event.imageByHost}, host: ${event.host}, enemy: ${event.enemy}")
                    if(event.moderationNeeded ){

                        gamesList.add(event)
                    }
                }
                Log.d(TAG, "callBack")
                callback(gamesList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                callback(mutableListOf<GameResult>())
            }
    }
    fun getImageFromStorage(pathToImage:String, callback: (Results<Bitmap?>) -> Unit) {
        // Create a reference to the image in Firebase Storage
        val imagePath = storageRef.child("$pathToImage")

        imagePath.getBytes(1024 * 1024)
            .addOnSuccessListener { imageBytes ->
                // Convert the byte array to a Bitmap
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                callback(Results.Success(bitmap))
            }
            .addOnFailureListener { exception ->
                callback(Results.Failure(exception))
            }
    }
    fun getEnemyPath(eventId: String): String?{
        return "results/$eventId/gameresultenemy.jpg"
    }
    fun getHostPath(eventId: String): String?{
        return "results/$eventId/gameresulthost.jpg"
    }
    fun markResultAsResolved(gameResult: GameResult){
        val document = firestore.collection("results").document(gameResult.rid)
        document.get()
            .addOnSuccessListener() {
                val game = GameResult.toResult(it)
                game.moderationNeeded = false
                document.update("moderationNeeded", game.moderationNeeded)
            }
            .addOnFailureListener() {
                // TODO
            }
    }
    fun signOffGame(gameIRL: GameIRL) {
        val document = firestore.collection("events").document(gameIRL.gid)

        document.get()
            .addOnSuccessListener() {
                val game = GameIRL.toGameIRL(it)
                val uid = ClientManager.getClient().uid
                if (game.host == "" || game.enemy == "") {
                    document.delete()
                } else {
                    if (game.host == uid) {
                        document.update("host", game.enemy)
                    } else {
                        document.update("enemy", "")
                    }
                }
            }
            .addOnFailureListener() {
                // TODO
            }
    }
}