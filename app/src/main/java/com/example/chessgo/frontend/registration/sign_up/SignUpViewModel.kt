package com.example.chessgo.frontend.registration.sign_up

import androidx.lifecycle.ViewModel

class SignUpViewModel() : ViewModel(){
    fun passwordValidator(password: String): Boolean {
        val minLength = 8
        val containsDigit = "(.*[0-9].*)"
        val containsLowerCase = "(.*[a-z].*)"
        val containsUpperCase = "(.*[A-Z].*)"
        val containsSpecialChar = "(.*[@#$%^&+=.!].*)"

        return password.length >= minLength &&
                password.matches(containsDigit.toRegex()) &&
                password.matches(containsLowerCase.toRegex()) &&
                password.matches(containsUpperCase.toRegex()) &&
                password.matches(containsSpecialChar.toRegex())
    }
}