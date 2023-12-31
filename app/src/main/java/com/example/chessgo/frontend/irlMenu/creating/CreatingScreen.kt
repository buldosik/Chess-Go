package com.example.chessgo.frontend.irlMenu.creating

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chessgo.R
import com.example.chessgo.frontend.navigation.navigateToMainMenu
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.time.LocalTime

private const val TAG = "CreatingMenuCompose"

@Composable
fun CreatingScreen(navController: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val assistant = remember { CreatingTools() }

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

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Description(
                description = description,
                onValueChange = {newDescription ->
                    description = newDescription
                })

            DateContainer(
                date = pickedDate,
                onDateChange = { newDate ->
                    Log.d(TAG, "Selected date: $newDate")
                    pickedDate = newDate
                },)

            TimeContainer(
                time = pickedTime,
                onTimeChange = {newTime ->
                    Log.d(TAG, "Selected time: $newTime")
                    pickedTime = newTime
                })

            // ToDo Update it--------------------------------------
            Position(position = pickedPoint) {
                togglePlacePicker()
            }

            // Create button
            CreateButton(
                onClick = {
                    // Sending to db
                    // ToDo add check for date & time
                    assistant.createEvent(
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

}
@Composable
fun Description(description: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = description,
        onValueChange = { updated -> onValueChange(updated) },
        enabled = true,
        label = { Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.titleSmall
            ) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            // ToDo make more gray
            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
        )
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
fun Position(position: LatLng, onClickEdit: () -> Unit) {
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
            label = { Text(
                    text = stringResource(id = R.string.latitude),
                    style = MaterialTheme.typography.titleMedium,
            ) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false,
            colors = standardOutlineTextColors()
        )

        OutlinedTextField(
            value = position.longitude.toString(),
            onValueChange = {  },
            label = { Text(
                    text = stringResource(id = R.string.longitude),
                    style = MaterialTheme.typography.titleMedium
                ) },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            enabled = false,
            colors = standardOutlineTextColors()
        )

        Button(
            onClick = { onClickEdit() },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
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
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
    ){
        Text(text = stringResource(id = R.string.create_event),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun Preview(){
    CreatingScreen()
}