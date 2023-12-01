package com.example.chessgo.frontend.create_irl_event


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CalenderViewModel(
    var selectedDate: Date,
    val monthDates: List<Date>
) {
    data class Date(
        var date: LocalDateTime,
        var isSelected: Boolean
    ) {

        var day: String = date.format(DateTimeFormatter.ofPattern("E")) // get the day by formatting the date
        var hour: Int = LocalDateTime.now().hour
        var minute: Int =  LocalDateTime.now().minute
    }
}

