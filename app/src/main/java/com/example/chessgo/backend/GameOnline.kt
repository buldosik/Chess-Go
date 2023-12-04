package com.example.chessgo.backend

import java.util.Date

class GameOnline(
    gid: String,
    date: Date,
    host: String,
    enemy: String,
    spectators: MutableList<String>,
    typeOfGame: String,
    result: String,
    status: String,
    val moves: MutableList<Move>,
) : Game(gid, date, host, enemy, typeOfGame, result, status) {
}