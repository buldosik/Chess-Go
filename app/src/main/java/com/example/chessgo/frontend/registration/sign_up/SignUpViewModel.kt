package com.example.chessgo.frontend.registration.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.registration.Results
import com.example.chessgo.backend.registration.sign_up.SignUpManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpViewModel : ViewModel(){
    private val signUpManager: SignUpManager = SignUpManager()
    private val _signUpResult = MutableLiveData<Results<FirebaseUser?>>()
    val signUpResult: LiveData<Results<FirebaseUser?>>
        get() = _signUpResult
    fun createAccount(email: String, userName: String, password: String){
        signUpManager.createUserWithEmailAndPassword(email, userName, password) {user, profileUpdates ->
            if (user != null && profileUpdates != null){
                updateDatabase(user, profileUpdates)
                _signUpResult.value = Results.Success(user)
            }
            else{
                _signUpResult.value = Results.Failure(IllegalArgumentException("Account with provided email already exists."))

            }
        }
    }
    private fun updateDatabase(user: FirebaseUser?, profileUpdates: UserProfileChangeRequest?){
        profileUpdates?.let {
            user?.updateProfile(it)?.addOnCompleteListener { profileUpdateTask ->
                if (profileUpdateTask.isSuccessful) {
                    signUpManager.saveUserToDatabase(user.displayName, user.email, user.uid)
                }
            }
        }
    }
}