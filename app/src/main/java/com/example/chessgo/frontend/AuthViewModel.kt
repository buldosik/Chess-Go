package com.example.chessgo.frontend

import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.registration.sign_in.SignInManager

class AuthViewModel : ViewModel() {
    val signInManager = SignInManager()
}