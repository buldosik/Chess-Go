package com.example.chessgo.frontend.mainmenu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chessgo.frontend.irlMenu.IRLMenuActivity
import com.example.chessgo.frontend.onlinegame.OnlineGameMenuActivity
import com.example.chessgo.frontend.registration.enterApp.EnterActivity
import com.example.chessgo.ui.theme.ChessgoTheme
import com.google.firebase.auth.FirebaseAuth

class MainMenuActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessgoTheme {
                MainScreen(
                    onSignOutClick = {
                        signOut()
                        toEnterActivity()
                    },
                    onIrlClick = { toIRLMenu() },
                    onOnlineClick = { toOnlineMenu() }
                )
            }
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
    fun toOnlineMenu() {
        val intent = Intent(applicationContext, OnlineGameMenuActivity::class.java)
        startActivity(intent)
    }
    fun toIRLMenu() {
        val intent = Intent(applicationContext, IRLMenuActivity::class.java)
        startActivity(intent)
    }

    fun toEnterActivity() {
        val intent = Intent(applicationContext, EnterActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // This will be called either automatically for you on 2.0
        // or later, or by the code above on earlier versions of the
        // platform.
        return
    }
}