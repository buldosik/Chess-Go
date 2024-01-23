package com.example.chessgo.frontend.onlinegame.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chessgo.backend.online.game.Position

@Composable
fun DrawAvailableMoves(
    selectedPiece: Position?,
    currentPosition: Position,
    onPieceMove: (Position) -> Unit
) {
    selectedPiece?.let { piece ->
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onPieceMove(currentPosition)
                }
        ) { }
    }
}