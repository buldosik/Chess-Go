package com.example.chessgo.frontend.loading

import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.global.ClientManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlin.random.Random

class LoadingViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    suspend fun initClient(callback: InitClientCallback) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            ClientManager.initClient(currentUser)
            val randomDelay = Random.nextLong(1000, 1500)
            delay(randomDelay)
            callback.onSuccess()
        }
        else {
            callback.onFail()
        }
    }
}
interface InitClientCallback {
    fun onSuccess()
    fun onFail()
}