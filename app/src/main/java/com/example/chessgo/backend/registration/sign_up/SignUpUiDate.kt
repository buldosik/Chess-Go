package com.example.chessgo.backend.registration.sign_up

data class SignUpUiDate(
    var email: String = "",
    var password: String = "",
    var userName: String = "",
    var remember: Boolean = false) {
    fun isNotEmpty(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && userName.isNotEmpty()
    }
}