package com.example.chessgo.backend.online.game

data class Position(val row: Int, val column: Int) {
    fun isValidPosition(): Boolean {
        return row in 0 until 8 && column in 0 until 8
    }

    operator fun plus(other: Position): Position =
        Position(this.row + other.row, this.column + other.column)
}