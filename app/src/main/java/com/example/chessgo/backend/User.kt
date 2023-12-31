package com.example.chessgo.backend

const val initRating = 1000

data class User(
    var uid : String,
    var username : String,
    var email : String,
    var isModerator: Boolean
) {
    val upcoming_events = listOf<String>()
    val history_games = listOf<String>()
    val personalRating = initRating
    val tournamentRating = initRating

    // TODO implementation of User class
}