package com.example.chessgo.backend.registration.sign_in

data class SignInUiDate(var email: String = "",
                        var password: String = "",
                        var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}