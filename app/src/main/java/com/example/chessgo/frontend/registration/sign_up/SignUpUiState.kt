package com.example.chessgo.frontend.registration.sign_up

data class SignUpUiState(
    var email: String = "",
    var password: String = "",
    var userName: String = "",
    var remember: Boolean = false) {
    fun isNotEmpty(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && userName.isNotEmpty()
    }
}