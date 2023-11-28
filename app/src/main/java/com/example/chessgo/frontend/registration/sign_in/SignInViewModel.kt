package com.example.chessgo.frontend.registration.sign_in

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_in.SignInManager
import com.example.chessgo.backend.registration.sign_in.SignInUiState
import com.example.chessgo.frontend.MainActivity
import com.google.firebase.auth.FirebaseUser

class SignInViewModel : ViewModel(){
    private val signInManager: SignInManager = SignInManager()
    private val _signInResult = MutableLiveData<Results<FirebaseUser?>>()
    val signInResult: LiveData<Results<FirebaseUser?>>
        get() = _signInResult

    fun signInWithEmailAndPassword(email: String, password: String) {

        signInManager.signInWithEmailAndPassword(email, password) { result ->
            _signInResult.value = result
        }
    }
}