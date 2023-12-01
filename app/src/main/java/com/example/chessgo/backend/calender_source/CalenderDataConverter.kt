package com.example.chessgo.backend.calender_source


import com.example.chessgo.frontend.create_irl_event.CalenderViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

class CalenderDataConverter {
    private val today = LocalDateTime.now()
    /*
    returns current month in the form
     plus days from the previous month if the month doesn't start on Monday
     current month days
     days from the next month if the month doesn't finish on Sunday
     */
    fun getDatesOfMonth(day: LocalDateTime): CalenderViewModel {
        val month = day.monthValue
        val year = day.year
        val daysInMonth = getDatesInMonth(month, year)

        val firstDayOfMonth = LocalDate.of(year, month, 1)
        val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1)


        val daysToAddAtStart = firstDayOfMonth.dayOfWeek.value - DayOfWeek.MONDAY.value
        val previousMonthDates =
            getDatesBetween(firstDayOfMonth.minusDays(daysToAddAtStart.toLong()), firstDayOfMonth.minusDays(1))

        val daysToAddAtEnd = DayOfWeek.SUNDAY.value - lastDayOfMonth.dayOfWeek.value
        val nextMonthDates = getDatesBetween(lastDayOfMonth.plusDays(1), lastDayOfMonth.plusDays(daysToAddAtEnd.toLong()))

        val allDates = previousMonthDates + daysInMonth + nextMonthDates

        return toUiModel(allDates, lastSelectedDate = today.toLocalDate())
    }

    private fun getDatesInMonth(month: Int, year: Int): List<LocalDate> {
        val firstDayOfMonth = LocalDate.of(year, month, 1)
        val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1)
        return getDatesBetween(firstDayOfMonth, lastDayOfMonth)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var current = startDate

        while (!current.isAfter(endDate)) {
            dates.add(current)
            current = current.plusDays(1)
        }

        return dates
    }
    /*
    converts List<LocalData> to CalenderViewModel
     */
    private fun toUiModel(dateList: List<LocalDate>, lastSelectedDate: LocalDate): CalenderViewModel {
        return CalenderViewModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            monthDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalenderViewModel.Date(
        isSelected = isSelectedDate,
        date = date.atStartOfDay(),
    )
}
