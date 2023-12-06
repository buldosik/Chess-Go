package com.example.chessgo.backend.global

import android.util.Log
import com.example.chessgo.backend.User

private const val TAG = "ClientManager"

class ClientManager {
    companion object {
        private lateinit var client : User
        fun initClient(user: User) {
            this.client = user
            Log.d(TAG, "User init successful")
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

