package com.example.chessgo.frontend.irlMenu.creating

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chessgo.R
import com.google.android.gms.maps.model.LatLng
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val TAG = "DATA_CONTAINERS"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContainer(date: LocalDate, onDateChange: (LocalDate) -> Unit) {
    val calendarState = rememberUseCaseState()

    var isError by remember {
        mutableStateOf(false)
    }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date {updatedDate ->
            isError = updatedDate.isBefore(LocalDate.now())
            Log.d(TAG, "Is error: $isError")
            onDateChange(updatedDate)
        }
    )
    InfoContainer(
        initValue = date.toString(),
        labelText = stringResource(id = R.string.date),
        onClick = { calendarState.show() },
        icon = Icons.Default.CalendarMonth,
        isError = isError
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeContainer(time: LocalTime, date: LocalDate, onTimeChange: (LocalTime) -> Unit) {
    val timePickerState = rememberTimePickerState(LocalTime.now().hour, LocalTime.now().minute, true)
    val context = LocalContext.current

    var isError by remember {
        mutableStateOf(false)
    }

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val newTime = LocalTime.of(hourOfDay, minute)
            isError = newTime.isBefore(LocalTime.now()) && date.isEqual(LocalDate.now())
            Log.d(TAG, "Is error: $isError")
            onTimeChange(newTime)
        },
        timePickerState.hour,
        timePickerState.minute,
        timePickerState.is24hour
    )
    InfoContainer(
        initValue = time.format(DateTimeFormatter.ofPattern("HH:mm")),
        labelText = stringResource(id = R.string.time),
        onClick = { timePickerDialog.show() },
        icon = Icons.Default.AccessTime,
        isError = isError
    )
}

@Composable
fun Address(address: String, onClickEdit: () -> Unit) {
    InfoContainer(
        initValue = address,
        labelText = stringResource(id = R.string.address),
        onClick = { onClickEdit() },
        icon = Icons.Default.LocationOn,
        isError = false
    )
}

@Composable
fun Position(position: LatLng, onClickEdit: () -> Unit) {
    InfoContainer(
        initValue = position.toString(),
        labelText = stringResource(id = R.string.position),
        onClick = { onClickEdit() },
        icon = Icons.Default.Map,
        isError = false
    )
}


@Composable
private fun standardOutlineTextColors() : TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        disabledTextColor = MaterialTheme.colorScheme.onBackground,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        disabledBorderColor = MaterialTheme.colorScheme.primary,
        disabledLabelColor = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
private fun errorOutlineTextColors() : TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        disabledTextColor = MaterialTheme.colorScheme.error,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        disabledBorderColor = MaterialTheme.colorScheme.error,
        disabledLabelColor = MaterialTheme.colorScheme.error,
    )
}

@Composable
fun InfoContainer(initValue: String, labelText: String, onClick: () -> Unit, icon: ImageVector, isError: Boolean) {
    Button(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        onClick = { onClick() },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ){
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = initValue, // assuming date is a Date or a String
            onValueChange = { },
            //modifier = Modifier.fillMaxSize(),
            enabled = false,
            label = {
                Text(
                    text = labelText,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint =
                        if(isError) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onBackground,
                )
            },
            colors =
                if(isError)errorOutlineTextColors()
                else standardOutlineTextColors(),
        )
    }
}