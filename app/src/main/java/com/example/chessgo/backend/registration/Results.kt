package com.example.chessgo.backend.registration

sealed class Results<out T> {
    data class Success<out T>(val data: T) : Results<T>()
    data class Failure(val exception: Exception?) : Results<Nothing>()
}