package com.example.chessgo.backend.global

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date


class TimeConverter {
    companion object {
        fun localDateTimeToDate(localDateTime: LocalDateTime): Date {
            // ToDo replace UTC by user Timezone
            return Date.from(localDateTime.toInstant(ZoneOffset.UTC))
        }
        fun convertToLocalDateTimeViaInstant(dateToConvert: Date): LocalDateTime {
            return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }
        fun convertToLocalDateViaInstant(dateToConvert: Date): LocalDate {
            return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }
        fun convertToLocalTimeViaInstant(dateToConvert: Date): LocalTime {
            return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }
    }
}