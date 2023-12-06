package com.example.chessgo.backend.global

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

class TimeConverter {
    companion object {
        fun localDateTimeToDate(localDateTime: LocalDateTime): Date {
            // ToDo replace UTC by user Timezone
            return Date.from(localDateTime.toInstant(ZoneOffset.UTC))
        }
        fun DateToLocalDate(date: Date): LocalDate {
            // ToDo make converter
            return LocalDate.now()
        }
    }
}