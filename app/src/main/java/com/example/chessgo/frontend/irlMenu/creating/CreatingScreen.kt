package com.example.chessgo.frontend.irlMenu.creating

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.chessgo.backend.global.GeocoderUtils
import com.example.chessgo.backend.global.LoadDataCallback
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
    var pickedAddress by remember {
        mutableStateOf("")
    }

    var isPlacePickerVisible by remember {
        mutableStateOf(false)
    }
    val togglePlacePicker: () -> Unit = {
        isPlacePickerVisible = !isPlacePickerVisible
    }

    var isAddressExists by remember {
        mutableStateOf(false)
    }
    val loadDataCallback = object: LoadDataCallback<String> {
        override fun onDataLoaded(response: String) {
            pickedAddress = response
            isAddressExists = true
        }

        override fun onDataNotAvailable(errorCode: Int, reasonMsg: String) {
            Log.d(TAG, "Error code: $errorCode, Message : $reasonMsg")
            isAddressExists = false
        }
    }

    val geocoderUtils = GeocoderUtils()

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

            DateContainer(
                date = pickedDate,
                onDateChange = { newDate ->
                    Log.d(TAG, "Selected date: $newDate")
                    pickedDate = newDate
                },)

            TimeContainer(
                time = pickedTime,
                date = pickedDate,
                onTimeChange = {newTime ->
                    Log.d(TAG, "Selected time: $newTime")
                    pickedTime = newTime
                })

            if(isAddressExists) {
                Address(address = pickedAddress) {
                    togglePlacePicker()
                }
            }
            else {
                Position(position = pickedPoint) {
                    togglePlacePicker()
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Description(
                description = description,
                onValueChange = {newDescription ->
                    description = newDescription
                })

            Spacer(modifier = Modifier.size(16.dp))
            
            CreateButton(
                onClick = {
                    // Sending to db
                    if(LocalDate.now().isAfter(pickedDate)) {
                        Toast.makeText(
                            context,
                            passedTimeMessage,
                            Toast.LENGTH_SHORT,
                        ).show()
                        return@CreateButton
                    }
                    if(LocalDate.now().isEqual(pickedDate) && LocalTime.now().isAfter(pickedTime)) {
                        Toast.makeText(
                            context,
                            passedTimeMessage,
                            Toast.LENGTH_SHORT,
                        ).show()
                        return@CreateButton
                    }
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
                geocoderUtils.getAddressFromPoint(context, pickedPoint, loadDataCallback) },
                togglePlacePicker = { togglePlacePicker() })
        }
    }

}
@Composable
fun Description(description: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .height(100.dp)
            .width(300.dp),
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