package com.example.chessgo.backend

const val initRating = 1000

data class User(
    var userId : String,
    var username : String,
    var email : String,
    var isModerator: Boolean
) {
    val personalRating = initRating
    val tournamentRating = initRating

    // TODO implementation of User class
}