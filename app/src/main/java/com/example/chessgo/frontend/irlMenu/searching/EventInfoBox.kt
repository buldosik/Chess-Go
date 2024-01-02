package com.example.chessgo.frontend.irlMenu.searching

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.chessgo.backend.GameIRL
import com.example.chessgo.backend.global.GeocoderUtils
import com.example.chessgo.backend.global.LoadDataCallback
import com.example.chessgo.backend.global.TimeConverter
import com.example.chessgo.frontend.irlMenu.standardOutlineTextColors
import com.example.chessgo.frontend.navigation.navigateToMainMenu
import com.google.android.gms.maps.model.LatLng
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date

private const val TAG = "EVENT_INFO_BOX"

@Composable
fun EventInfoBox(chosenMarkerGID: String, searchingTools: SearchingTools, navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        val context = LocalContext.current

        var gameIrl by remember { mutableStateOf(
            GameIRL(
                position = LatLng(0.0, 0.0),
                date = Date(),
            )
        ) }

        var pickedAddress by remember {
            mutableStateOf("")
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

        searchingTools.searchingIRLManager.getInfoAboutEvent(chosenMarkerGID) {
            if (it != null) {
                gameIrl = it
                geocoderUtils.getAddressFromPoint(context, gameIrl.position, loadDataCallback)
            }
        }

        val description by remember {
            mutableStateOf(gameIrl.description)
        }
        val pickedDate by remember {
            mutableStateOf(TimeConverter.convertToLocalDateViaInstant(gameIrl.date))
        }
        val pickedTime by remember {
            mutableStateOf(TimeConverter.convertToLocalTimeViaInstant(gameIrl.date))
        }
        val pickedPoint by remember {
            mutableStateOf(gameIrl.position)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            OutlinedTextField(
                value = description,
                onValueChange = {  },
                label = { Text(
                    text = "Description",
                    color = MaterialTheme.colorScheme.onBackground
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = standardOutlineTextColors(),
                enabled = false
            )
            OutlinedTextField(
                value = pickedDate.atTime(pickedTime)
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)),
                onValueChange = {  },
                label = { Text(
                    text = "Date & Time",
                    color = MaterialTheme.colorScheme.onBackground
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = standardOutlineTextColors(),
                enabled = false
            )
            var text by remember {
                mutableStateOf("")
            }
            text = if(isAddressExists)
                pickedAddress
            else
                pickedPoint.toString()
            OutlinedTextField(
                value = text, // assuming date is a Date or a String
                onValueChange = { },
                label = { Text(
                    text = if(isAddressExists)"Address" else "Point",
                    color = MaterialTheme.colorScheme.onBackground
                ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = standardOutlineTextColors(),
                enabled = false
            )
            Button(
                onClick = {
                    // ToDo apply to event
                    navController.navigateToMainMenu()
                },
                modifier = Modifier
            ) {
                Text("Apply")
            }
        }
    }
}