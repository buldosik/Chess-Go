package com.example.chessgo.frontend.irlMenu.creating

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
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
fun CreatingMenu(navController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val viewModel = remember { CreatingViewModel() }

    var description by remember {
        mutableStateOf("")
    }
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var pickedPoint by remember {
        mutableStateOf(LatLng(0.0,0.0))
    }

    var isPlacePickerVisible by remember {
        mutableStateOf(false)
    }
    // Function to show/hide the PlacePicker
    val togglePlacePicker: () -> Unit = {
        isPlacePickerVisible = !isPlacePickerVisible
    }

    val passedTimeMessage = stringResource(id = R.string.passed_time_message)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Row 1: Description field & "edit" button
        Description(
            description = description,
            onValueChange = {newDescription ->
                description = newDescription
            })

        // Row 2: Date field & "edit" button
        DateRow(
            date = pickedDate,
            onValueChange = { newDate ->
                Log.d(TAG, "Selected date: $newDate")
                if (newDate == LocalDate.now()){
                    pickedTime = LocalTime.now()
                }
                pickedDate = newDate
        },
            listOfDisabledDates = viewModel.getListOfDisabledDates())

        // Row 3: Time field & "edit" button
        TimeRow(
            time = pickedTime,
            onValueChange = {newTime ->
                Log.d(TAG, "Selected time: $newTime")
                if (pickedDate == LocalDate.now() && newTime.isBefore(pickedTime)){
                    Toast.makeText(context, passedTimeMessage, Toast.LENGTH_SHORT).show()
                }else{
                    pickedTime = newTime
                }
            },
            context = context)

        // Row 4: LatLng fields & "edit" button
        Position(position = pickedPoint) {
            togglePlacePicker()
        }

        // Create button
        CreateButton(
            onClick = {
                // Sending to db
                viewModel.createEvent(
                    description = description,
                    date = pickedDate,
                    time = pickedTime,
                    position = pickedPoint)
                // Back to main menu
                navController.navigateToMainMenu()
            })
    }
    if (isPlacePickerVisible) {
        PlacePicker(onPlacePickerVisibilityChanged = {newPickedPoint->
            pickedPoint = newPickedPoint
            togglePlacePicker() })
    }

}

@Composable
fun Description(description: String, onValueChange: (String) -> Unit) {
    val descriptionText = stringResource(id = R.string.description)
    Spacer(modifier = Modifier.size(45.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = description,
            onValueChange = { updated -> onValueChange(updated) },
            modifier = Modifier
                .weight(1f),
            enabled = true,
            label = {
                Text(
                    text = descriptionText,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight =  MaterialTheme.typography.titleMedium.fontWeight
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRow(date: LocalDate, onValueChange: (LocalDate) -> Unit, listOfDisabledDates: List<LocalDate>) {
    val calendarState = rememberUseCaseState()
    Spacer(modifier = Modifier.size(30.dp))
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH,
            disabledDates = listOfDisabledDates,
        ),
        selection = CalendarSelection.Date {
                updatedDate -> onValueChange(updatedDate)}
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = date.toString(), // assuming date is a Date or a String
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false,
            onValueChange = { },
            label = {
                Text(text = stringResource(id = R.string.date),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight =  MaterialTheme.typography.titleMedium.fontWeight
            ) },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.onBackground,
            )
        )

        Button(
            onClick = {
                /* handle edit button click */
                calendarState.show()
            },

            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            /*
            CustomTextField(text= stringResource(id = R.string.edit))
            */
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRow(time: LocalTime, onValueChange: (LocalTime) -> Unit, context: Context) {
    val timePickerState = rememberTimePickerState(LocalTime.now().hour, LocalTime.now().minute, true)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val newDate = LocalTime.of(hourOfDay, minute)
            onValueChange(newDate)
        },
        timePickerState.hour,
        timePickerState.minute,
        timePickerState.is24hour
    )
    Spacer(modifier = Modifier.size(30.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = time.format(DateTimeFormatter.ofPattern(stringResource(id = R.string.time_pattern))), // assuming date is a Date or a String
            onValueChange = { },
            label = { Text(text = stringResource(id = R.string.time),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight =  MaterialTheme.typography.titleMedium.fontWeight
            )
                    },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.onBackground,
            )
        )

        Button(
            onClick = { timePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        ) {
            /*
            CustomTextField(text= stringResource(id = R.string.edit))
            */
            Icon(
                imageVector = Icons.Default.MoreTime,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
@Composable
fun Position(position: LatLng, onClickEdit: () -> Unit) {

    Spacer(modifier = Modifier.size(30.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Assuming LatLng is a data class with two Double properties: latitude and longitude
        OutlinedTextField(
            value = position.latitude.toString(),
            onValueChange = {  },
            label = { Text(text = stringResource(id = R.string.latitude),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight =  MaterialTheme.typography.titleMedium.fontWeight
            ) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onBackground,

                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.onBackground,
            )
        )

        OutlinedTextField(
            value = position.longitude.toString(),
            onValueChange = {  },
            label = { Text(text = stringResource(id = R.string.longitude),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight =  MaterialTheme.typography.titleMedium.fontWeight
            ) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.onBackground,
            )
        )

        Button(
            onClick = {
                onClickEdit() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            /*
            CustomTextField(text= stringResource(id = R.string.edit))
           */
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
fun CreateButton(onClick: () -> Unit) {
    Spacer(modifier = Modifier.size(30.dp))
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
    ){
        CustomTextField(text= stringResource(id = R.string.create_event))
    }

}

@Composable
fun CustomTextField(text : String){
    Text(text = text,
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
        fontWeight =  MaterialTheme.typography.titleMedium.fontWeight,
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
        )
    )
}
@Preview
@Composable
fun Preview(){
    CreatingMenu()
}