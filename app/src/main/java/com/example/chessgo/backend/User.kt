package com.example.chessgo.backend

const val initRating = 1000

data class User(
    var userId : String,
    var username : String,
    var email : String,
) {
    val personalRating = initRating
    val tournamentRating = initRating

    // TODO implementation of User class

}