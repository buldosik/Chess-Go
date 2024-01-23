package com.example.chessgo.frontend.onlinegame.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex
import com.example.chessgo.backend.online.game.Position

@Composable
fun ChessBoardBox(
    modifier: Modifier = Modifier,
    viewModel: ChessBoardViewModel = ChessBoardViewModel()
) {
    var cellSize by remember { mutableStateOf(IntSize(50, 50)) }
    val selectedPosition = remember { mutableStateOf<Position?>(null) }

    Box (
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        // Board
        Column {
            viewModel.cells.forEachIndexed { rowIndex, row ->
                Row {
                    row.forEachIndexed { columnIndex, _ ->
                        val currentPosition = Position(rowIndex, columnIndex)
                        val cellColor = determineCellColor(
                            Position(rowIndex, columnIndex),
                            selectedPosition,
                        )

                        Box(modifier = Modifier
                            .weight(1f)
                            .onSizeChanged { if (cellSize.width == 0) cellSize = it }
                            .aspectRatio(1f)
                            .background(cellColor),
                            contentAlignment = Alignment.Center)
                        {
                            if(selectedPosition.value != null && viewModel.isMyTurn()) {
                                DrawAvailableMoves(
                                    selectedPiece = selectedPosition.value,
                                    currentPosition = currentPosition,
                                ) { targetPosition ->
                                    if (viewModel.isMyTurn()) {
                                        selectedPosition.value?.let {
                                            viewModel.makeMove(
                                                it,
                                                targetPosition
                                            )
                                        }
                                        selectedPosition.value = null
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // Pieces
        Column {
            viewModel.cells.forEachIndexed { i, row ->
                Row {
                    row.forEachIndexed { j, piece ->
                        Box(
                            modifier = Modifier
                                .zIndex(2f)
                                .weight(1f)
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if (piece != null) {
                                PieceView(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    //.offset { offsetState }
                                    piece = piece,
                                    onPieceClick = {
                                        if(viewModel.isMyTurn()) {
                                            handlePieceClick(
                                                currentPosition = Position(i, j),
                                                selectedPosition = selectedPosition,
                                                viewModel = viewModel,
                                                onPieceMove = { targetPosition ->
                                                    selectedPosition.value?.let {
                                                        viewModel.makeMove(
                                                            it,
                                                            targetPosition
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun determineCellColor(position: Position, selectedPosition: MutableState<Position?>): Color {
    return if ((position.row + position.column) % 2 != 0)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.onPrimaryContainer
}

private fun handlePieceClick(
    currentPosition: Position,
    selectedPosition: MutableState<Position?>,
    viewModel: ChessBoardViewModel,
    onPieceMove: (Position) -> Unit
) {
    /*val isCapturable = selectedPosition.value?.let { piece ->
        chessBoardState.isOccupiedByOpponent(currentPiece.position, piece.color) &&
                piece.getAvailableMoves(chessBoardState).contains(currentPiece.position)
    } ?: false*/
    if (selectedPosition.value != null) {
        selectedPosition.value?.let { onPieceMove.invoke(currentPosition) }
        selectedPosition.value = null

    } else if (viewModel.getPiece(currentPosition)?.color  == viewModel.player) {
        selectedPosition.value = currentPosition
    }
}