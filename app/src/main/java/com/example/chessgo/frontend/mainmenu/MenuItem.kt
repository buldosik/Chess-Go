package com.example.chessgo.frontend.mainmenu

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    var id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)
