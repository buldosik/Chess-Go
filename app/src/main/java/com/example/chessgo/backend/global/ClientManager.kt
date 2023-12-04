package com.example.chessgo.backend.global

import com.example.chessgo.backend.User

class ClientManager {
    companion object {
        private lateinit var client : User
        fun User.initClient() {
            this@Companion.client = this
        }
        fun getClient(): User {return client}
        fun setClientInfo(uid : String = client.userId,
                          name : String = client.username,
                          email : String = client.email) {
            client.userId = uid
            client.username = name
            client.email = email
        }
    }
}

