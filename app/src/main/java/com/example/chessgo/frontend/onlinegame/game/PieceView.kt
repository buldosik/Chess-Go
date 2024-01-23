package com.example.chessgo.frontend.onlinegame.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chessgo.R
import com.example.chessgo.backend.online.game.ChessPiece
import com.example.chessgo.backend.online.game.PieceColor
import com.example.chessgo.backend.online.game.PieceType

@Composable
fun PieceView(
    modifier: Modifier = Modifier,
    piece: ChessPiece,
    onPieceClick: () -> Unit
) {
    val imageResId = when (piece.type) {
        PieceType.King -> if (piece.color == PieceColor.White) R.drawable.white_king else R.drawable.black_king
        PieceType.Queen -> if (piece.color == PieceColor.White) R.drawable.white_queen else R.drawable.black_queen
        PieceType.Rook -> if (piece.color == PieceColor.White) R.drawable.white_rook else R.drawable.black_rook
        PieceType.Bishop -> if (piece.color == PieceColor.White) R.drawable.white_bishop else R.drawable.black_bishop
        PieceType.Knight -> if (piece.color == PieceColor.White) R.drawable.white_knight else R.drawable.black_knight
        PieceType.Pawn -> if (piece.color == PieceColor.White) R.drawable.white_pawn else R.drawable.black_pawn
    }

    Image(
        painter = painterResource(imageResId),
        contentDescription = "${piece.type} ${piece.color}",
        modifier = modifier
            .padding(6.dp)
            .clickable { onPieceClick() }
    )
}