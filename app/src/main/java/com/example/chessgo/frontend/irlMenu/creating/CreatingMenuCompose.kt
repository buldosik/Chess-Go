package com.example.chessgo.frontend.irlMenu.creating

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chessgo.frontend.navigation.navigateToMainMenu
import com.google.android.gms.maps.model.LatLng
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val TAG = "CreatingMenuCompose"

@Composable
fun CreatingMenu(navController: NavHostController) {
    val viewModel = remember { CreatingViewModel() }

    var description by remember {
        mutableStateOf(viewModel.description)
    }
    var pickedDate by remember {
        mutableStateOf(viewModel.pickedDate)
    }
    var pickedTime by remember {
        mutableStateOf(viewModel.pickedTime)
    }
    var pickedPoint by remember {
        mutableStateOf(viewModel.pickedPoint)
    }

    var isPlacePickerVisible by remember {
        mutableStateOf(false)
    }
    // Function to show/hide the PlacePicker
    val togglePlacePicker: () -> Unit = {
        isPlacePickerVisible = !isPlacePickerVisible
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Row 1: Description field & "edit" button
        Description(
            description = description,
            onValueChange = {newDescription ->
                description = newDescription
                viewModel.description = newDescription
            })

        // Row 2: Date field & "edit" button
        DateRow(
            date = pickedDate,
            onValueChange = { newDate ->
                Log.d(TAG, "Selected date: $newDate")
                pickedDate = newDate
                viewModel.pickedDate = newDate
        })

        // Row 3: Time field & "edit" button
        TimeRow(
            time = pickedTime,
            onValueChange = {newTime ->
                Log.d(TAG, "Selected time: $newTime")
                pickedTime = newTime
                viewModel.pickedTime = newTime
            })

        // Row 4: LatLng fields & "edit" button
        Position(position = pickedPoint) {
            togglePlacePicker()
        }

        // Create button
        CreateButton(
            onClick = {
                // Sending to db
                viewModel.createEvent()
                // Back to main menu
                navController.navigateToMainMenu()
            })
    }
    if (isPlacePickerVisible) {
        PlacePicker(viewModel = viewModel, onPlacePickerVisibilityChanged = { togglePlacePicker() })
    }

}

@Composable
fun Description(description: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = description,
            onValueChange = { updated -> onValueChange(updated) },
            label = { Text("Description") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRow(date: LocalDate, onValueChange: (LocalDate) -> Unit) {
    val calendarState = rememberUseCaseState()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date { updatedDate -> onValueChange(updatedDate) }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = date.toString(), // assuming date is a Date or a String
            onValueChange = {  },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRow(time: LocalTime, onValueChange: (LocalTime) -> Unit) {
    val timePickerState = rememberTimePickerState(LocalTime.now().hour, LocalTime.now().minute, true)
    val context = LocalContext.current

    val timePickerDialog = TimePickerDialog(
        context,
        { view, hourOfDay, minute ->
            var newDate = LocalTime.of(hourOfDay, minute)
            onValueChange(newDate)
        },
        timePickerState.hour,
        timePickerState.minute,
        timePickerState.is24hour
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = time.format(DateTimeFormatter.ofPattern("HH:mm")), // assuming date is a Date or a String
            onValueChange = { },
            label = { Text("Time") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false
        )

        Button(
            onClick = { timePickerDialog.show() },
            modifier = Modifier
                .height(48.dp)
        ) {
            Text("Edit")
        }
    }
}

@Composable
fun Position(position: LatLng, onClickEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Assuming LatLng is a data class with two Double properties: latitude and longitude
        OutlinedTextField(
            value = position.latitude.toString(),
            onValueChange = {  },
            label = { Text("Latitude") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false
        )

        OutlinedTextField(
            value = position.longitude.toString(),
            onValueChange = {  },
            label = { Text("Longitude") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false
        )

        Button(
            onClick = { onClickEdit() },
            modifier = Modifier
                .height(48.dp)
        ) {
            Text("Edit")
        }
    }
}

@Composable
fun CreateButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
        .height(48.dp)) {
        Text("Create")
    }
}