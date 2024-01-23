package com.example.chessgo.frontend.onlinegame.game

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chessgo.backend.online.game.ChessPiece
import com.example.chessgo.backend.online.game.PieceColor
import com.example.chessgo.backend.online.game.PieceType
import com.example.chessgo.backend.online.game.Position
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

private const val TAG = "ChessBoardViewModel"

class ChessBoardViewModel: ViewModel() {
    private val auth = Firebase.auth
    private val db = Firebase.database
    private val functions = Firebase.functions

    private var _cells: MutableList<MutableList<ChessPiece?>> = MutableList(0) { MutableList(0) { null } }
    //var cells = mutableStateListOf <MutableList<ChessPiece?>>()
    @SuppressLint("MutableCollectionMutableState")
    var cells = mutableStateListOf(mutableStateListOf<ChessPiece?>(null))

    private fun initializeBoard(rows: Int, columns: Int) {
        _cells = MutableList(rows) { MutableList(columns) { null } }
    }

    fun getPiece(row: Int, column: Int): ChessPiece? {
        return _cells[row][column]
    }

    fun getPiece(position: Position): ChessPiece? {
        return _cells[position.row][position.column]
    }

    fun setCellsFromString(board: String) {
        val rows = board.split('#')
        cells.clear()
        rows.forEachIndexed { row, it->
            val item = it.split(',')
            cells.add(mutableStateListOf())
            item.forEachIndexed { column, cell ->
                _cells[row][column] = if (cell == "..") null else getChessPieceFromString(cell)
            }
            cells[row].addAll(_cells[row])
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

    var turn : PieceColor = PieceColor.White
    var player : PieceColor = PieceColor.White
    private var lobbyID = ""

    suspend fun init(lobbyID: String, toggleLoading: () -> Unit, toggleTemp: () -> Unit) {
        initializeBoard(8,8)
        this.lobbyID = lobbyID
        delay(4000)
        getBoardFromLobby(toggleLoading, toggleTemp)
        delay(2000)
        toggleLoading()
    }

    private fun getBoardFromLobby(toggleLoading: () -> Unit, toggleTemp: () -> Unit) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value: Map<String, Any?> = snapshot.getValue<Map<String, Any?>>() ?: return
                //Log.d(TAG, "$value")
                //Log.d(TAG, "${value["turn"]}")
                turn = if(value["turn"].toString() == "W") { PieceColor.White } else { PieceColor.Black }

                //Log.d(TAG, "${value["white"]}")
                val white = value["white"].toString()
                player = if(white == auth.uid) { PieceColor.White } else { PieceColor.Black }

                //Log.d(TAG, "${value["board"]}")
                val chessBoardStr = value["board"].toString()
                setCellsFromString(chessBoardStr)
                toggleTemp()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "CANCELLED ON SOME REASON")
            }
        }
        db.getReference("lobbies/${lobbyID}").addValueEventListener(postListener)
    }

    fun makeMove(startPosition: Position, endPosition: Position) {
        Log.d(TAG, "$startPosition ## $endPosition")
        val move = moveToString(startPosition, endPosition)
        Log.d(TAG, move)
        val data: Map<String, Any?> = mapOf(
            "lobby_id" to lobbyID,
            "move" to move,
        )
        Log.d(TAG, data.toString())
        functions.getHttpsCallable("make_a_move")
            .call(data)
            .addOnSuccessListener {
                Log.d(TAG, "Success")
            }
            .addOnCanceledListener {
                Log.d(TAG, "Cancel")
            }
            .addOnFailureListener {
                Log.d(TAG, "Fail")
            }
    }

    private fun moveToString(startPosition: Position, endPosition: Position): String {
        val playerStr = if(player == PieceColor.White) "W" else "B"
        val startStr = startPosition.row.toString() + startPosition.column.toString()
        val endStr = endPosition.row.toString() + endPosition.column.toString()
        return "$playerStr,$startStr,$endStr"
    }

    fun isMyTurn(): Boolean {
        return player == turn
    }

}