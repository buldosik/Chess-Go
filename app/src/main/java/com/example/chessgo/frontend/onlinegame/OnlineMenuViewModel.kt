package com.example.chessgo.frontend.onlinegame

import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.online.OnlineMenuManager

class OnlineMenuViewModel : ViewModel(){
    private val onlineManager = OnlineMenuManager()

    fun startMatchmaking(callback: (String) -> Unit) {
        onlineManager.startMatchmaking(callback)
    }
}