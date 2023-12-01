package com.example.chessgo.frontend.irlMenu.creating

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val TAG = "CretingMenu"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingMenu(
    viewModel: CreatingViewModel,
    creatingMenuActivity: CreatingMenuActivity
) {
    var description by remember {
        mutableStateOf("")
    }

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Row 1: Description field & "edit" button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = description,
                onValueChange = { newValue -> description = newValue },
                label = { Text("Description") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                enabled = true
            )
        }

        val calendarState = rememberUseCaseState()

        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true,
                style = CalendarStyle.MONTH,
            ),
            selection = CalendarSelection.Date { date ->
            Log.d(TAG, "Selected date: $date")
            pickedDate = date
            }
        )

        // Row 2: Date field & "edit" button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = pickedDate.toString(), // assuming date is a Date or a String
                onValueChange = {/* update view model or handle change */ },
                label = { Text("Date") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                enabled = false
            )

            Button(
                onClick = {
                          /* handle edit button click */
                    calendarState.show()
                },
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text("Edit")
            }
        }

        val timePickerState = rememberUseCaseState()
        DateTimeDialog(
            state = timePickerState,
            selection = DateTimeSelection.Time {newTime ->
                pickedTime = newTime
            })

        // Row 3: Time field & "edit" button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = pickedTime.format(DateTimeFormatter.ofPattern("HH:mm")), // assuming date is a Date or a String
                onValueChange = {
                    /* update view model or handle change */
                },
                label = { Text("Time") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                enabled = false
            )

            Button(
                onClick = {
                          /* handle edit button click */
                          timePickerState.show()
                          },
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text("Edit")
            }
        }

        // Row 4: LatLng fields & "edit" button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Assuming LatLng is a data class with two Double properties: latitude and longitude
            OutlinedTextField(
                value = viewModel.lastPoint.latitude.toString(),
                onValueChange = { /* update view model or handle change */ },
                label = { Text("Latitude") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                enabled = false
            )

            OutlinedTextField(
                value = viewModel.lastPoint.longitude.toString(),
                onValueChange = { /* update view model or handle change */ },
                label = { Text("Longitude") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                enabled = false
            )

            Button(
                onClick = {
                          /* handle edit button click */
                          creatingMenuActivity.goToPlacePicker()
                          },
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text("Edit")
            }
        }

        Button(
            onClick = {
                // ToDo sent to db info

                creatingMenuActivity.goToMainMenu()
            },
            modifier = Modifier
                .height(48.dp)) {
            Text("Create")
        }
    }
}
