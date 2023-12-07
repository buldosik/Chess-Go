package com.example.chessgo.frontend.onlinegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.example.chessgo.ui.theme.ChessgoTheme

class OnlineGameMenuActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setContent {
            ChessgoTheme {
               Text("online game menu")
            }
        }
    }
    /*
    @Composable
    fun CenterAlignedTopAppBarExample() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "Centered Top App Bar",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { innerPadding ->
            ScrollContent(innerPadding)
        }
    }
    */
}