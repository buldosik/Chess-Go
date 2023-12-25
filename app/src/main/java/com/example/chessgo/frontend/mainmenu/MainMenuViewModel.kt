package com.example.chessgo.frontend.mainmenu

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainMenuViewModel : ViewModel() {
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}