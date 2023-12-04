package com.example.chessgo.frontend

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.chessgo.frontend.registration.sign_in.SignInActivity
import com.example.chessgo.frontend.registration.sign_up.SignUpActivity
import com.example.chessgo.ui.theme.ChessgoTheme

class MainActivity : ComponentActivity() {

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessgoTheme {
                MainApp(authViewModel = authViewModel, activity = this)
            }
        }
    }
//    override fun onBackPressed() {
//        super.onBackPressed()
//        // This will be called either automatically for you on 2.0
//        // or later, or by the code above on earlier versions of the
//        // platform.
//        return
//    }
}