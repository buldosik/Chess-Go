package com.example.chessgo.backend.global

import android.net.Uri
import android.util.Log
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.User
import com.google.firebase.auth.FirebaseUser

private const val TAG = "ClientManager"

class ClientManager {
    companion object {
        private lateinit var client : User
        lateinit var userGameIRL : GameIRL
        lateinit var currentPicture : String
        var pictureUri : Uri? = null
        var isPictureChanged = false
        fun initClient(user: User) {
            this.client = user
            Log.d(TAG, "User init successful")
        }
        fun initClient(user: FirebaseUser) {
            user.toUser().addOnSuccessListener { client ->
                // Successfully obtained the User object
                initClient(client)
            }.addOnFailureListener { exception ->
                // Handle failure, e.g., show an error message
                Log.e(TAG, "Error initializing client", exception)
            }
        }
        fun getClient(): User {return client}
        fun setClientInfo(uid : String = client.uid,
                          name : String = client.username,
                          email : String = client.email) {
            client.uid = uid
            client.username = name
            client.email = email
        }
    }
}

