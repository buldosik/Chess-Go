package com.example.chessgo.backend.online.game

private const val TAG = "ChessBoard"

class ChessBoard {
    lateinit var cells: MutableList<MutableList<ChessPiece?>>

    fun initializeBoard(rows: Int, columns: Int) {
        cells = MutableList(rows) { MutableList(columns) { null } }
    }

    fun getPiece(row: Int, column: Int): ChessPiece? {
        return cells[row][column]
    }

    fun getPiece(position: Position): ChessPiece? {
        return cells[position.row][position.column]
    }

    fun setCellsFromString(board: String) {
        val _rows = board.split('#')
        _rows.forEachIndexed { row, it->
            val _cells = it.split(',')
            _cells.forEachIndexed { column, cell ->
                cells[row][column] = if (cell == "..") null else getChessPieceFromString(cell)
            }
        }
    }

    private fun getChessPieceFromString(piece: String): ChessPiece {
        val color = if (piece[0] == 'W') PieceColor.White else PieceColor.Black
        val pieceType = when(piece.substring(1, 3)) {
            "Pa" -> PieceType.Pawn
            "Bi" -> PieceType.Bishop
            "Ki" -> PieceType.King
            "Kn" -> PieceType.Knight
            "Qu" -> PieceType.Queen
            "Ro" -> PieceType.Rook
            else -> { throw IllegalArgumentException("Invalid piece: $piece") }
        }
        return ChessPiece(color, pieceType)
    }
}