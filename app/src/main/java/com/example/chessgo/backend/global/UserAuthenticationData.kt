package com.example.chessgo.backend.global

import android.content.Context

data class Credential (
        val email: String,
        val password: String,
        ) {}

class UserAuthenticationData {
    companion object {
        fun storeAuthenticationData(credential: Credential, context: Context) {
            context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                .edit()
                .putString("email", credential.email)
                // ToDo change to encrypted password
                .putString("password", credential.password)
                .apply()
        }
        fun getAuthenticationData(context: Context) : Credential {
            val email = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                .getString("email", "")
            val password = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                .getString("password", "")
            return Credential(email!!, password!!)
        }
        fun removeAuthenticationData(context: Context) {
            val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // Remove a value by providing the key
            editor.remove("email")
            editor.remove("password")

        }
    }
}