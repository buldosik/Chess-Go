package com.example.chessgo.backend.global

import com.example.chessgo.backend.User

class ClientManager {
    companion object {
        private lateinit var client : User
        fun initClient(uid : String, name : String, email : String) {
            client = User(
                userId = uid,
                username = name,
                email = email,
            )
        }
        fun getClientInfo(): User {return client}
        fun setClientInfo(uid : String = client.userId,
                          name : String = client.username,
                          email : String = client.email) {
            client.userId = uid
            client.username = name
            client.email = email
        }

    }
}

