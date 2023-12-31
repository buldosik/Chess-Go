package com.example.chessgo.frontend.irlMenu.creating

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.chessgo.R
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
private fun standardOutlineTextColors() : TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        disabledTextColor = MaterialTheme.colorScheme.onBackground,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        disabledBorderColor = MaterialTheme.colorScheme.primary,
        disabledLabelColor = MaterialTheme.colorScheme.onBackground,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContainer(date: LocalDate, onDateChange: (LocalDate) -> Unit) {
    val calendarState = rememberUseCaseState()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date { updatedDate -> onDateChange(updatedDate) }
    )
    Button(
        onClick = { calendarState.show() },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        OutlinedTextField(
            value = date.toString(), // assuming date is a Date or a String
            onValueChange = { },
            enabled = false,
            label = {
                Text(
                    text = stringResource(id = R.string.date),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            },
            colors = standardOutlineTextColors(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeContainer(time: LocalTime, onTimeChange: (LocalTime) -> Unit) {
    val timePickerState = rememberTimePickerState(LocalTime.now().hour, LocalTime.now().minute, true)
    val context = LocalContext.current

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val newDate = LocalTime.of(hourOfDay, minute)
            onTimeChange(newDate)
        },
        timePickerState.hour,
        timePickerState.minute,
        timePickerState.is24hour
    )

    Button(
        onClick = { timePickerDialog.show() },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ){
        OutlinedTextField(
            value = time.format(DateTimeFormatter.ofPattern(stringResource(id = R.string.time_pattern))), // assuming date is a Date or a String
            onValueChange = { },
            //modifier = Modifier.fillMaxSize(),
            enabled = false,
            label = {
                Text(
                    text = stringResource(id = R.string.time),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            },
            colors = standardOutlineTextColors(),
        )
    }
}