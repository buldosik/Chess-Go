package com.example.chessgo.frontend.create_irl_event

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chessgo.backend.calender_source.CalenderDataConverter
import com.example.chessgo.ui.theme.ChessgoTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PickUpDateActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ChessgoTheme {
                Calender()
            }
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun Calender(){
    val coroutineScope = rememberCoroutineScope()

    val dataCalender = CalenderDataConverter()
    //currentDate is Used for arrows back and forward to load new months
    val currentDate  = remember { mutableStateOf(LocalDateTime.now()) }
    val calenderViewModel = remember {mutableStateOf(dataCalender.getDatesOfMonth(currentDate.value))}
    // stores chosen time and day.
    val selectedTimeDate = remember { mutableStateOf(Triple(LocalDateTime.now(), 0, 0)) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Header(
            data = calenderViewModel.value,
            onPreviousClick = { newDate ->
                //waits for change of ui coroutine
                coroutineScope.launch {
                    currentDate.value = currentDate.value.minusMonths(newDate)
                    calenderViewModel.value = dataCalender.getDatesOfMonth(currentDate.value)
                }
            },
            onNextClick = { newDate ->
                coroutineScope.launch {
                    currentDate.value = currentDate.value.plusMonths(newDate)
                    calenderViewModel.value = dataCalender.getDatesOfMonth(currentDate.value)
                }
            }
        )

        Spacer(modifier = Modifier.height(60.dp))

        MainComponent(
            data = calenderViewModel.value,
            currentDate = currentDate.value,
            onItemClick = {newDateTime ->
                coroutineScope.launch{
                    selectedTimeDate.value = newDateTime
                }
            }
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(onClick = {/* TODO ADD DATA TO DATABASE*/
            println("${selectedTimeDate.value.first}, ${selectedTimeDate.value.second}, ${selectedTimeDate.value.third}")
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text("Confirm")


        }
        Spacer(modifier = Modifier.height(60.dp))
    }

}


@Composable
fun Header(data: CalenderViewModel, onPreviousClick: (Long) -> Unit, onNextClick: (Long) -> Unit){
    Row {
        Text(
            text = data.selectedDate.date.format(DateTimeFormatter.ofPattern("d-MM-uuuu")),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )

        IconButton(onClick = {onPreviousClick(1) }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Previous"
            )
        }
        IconButton(onClick = {onNextClick(1) } ){
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Next"

            )
        }
    }
}

@Composable
fun MainComponent(
    data: CalenderViewModel,
    currentDate: LocalDateTime,
    onItemClick: (Triple<LocalDateTime, Int, Int>) -> Unit
){
    LazyColumn {
        items(items = data.monthDates.chunked(7)) { weekDates ->
            Row {
                weekDates.forEach { date ->
                    // picks different colors for current and next/prev months
                    val backgroundColor = if (date.date.monthValue == currentDate.monthValue) Color.White
                    else Color.Gray
                    val contentColor = if (date.date.monthValue == currentDate.monthValue) MaterialTheme.colors.primary
                    else Color.Black

                    DayItem(
                        date = date,
                        backgroundColor = backgroundColor,
                        contentColor = contentColor,
                        onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
fun DayItem(
    date: CalenderViewModel.Date,
    backgroundColor: Color,
    contentColor: Color,
    onItemClick: (Triple<LocalDateTime, Int, Int>) -> Unit
) {

    val context = LocalContext.current
    val isPastDate = date.date.isBefore(LocalDateTime.now().minusDays(1))
    val hour = date.hour
    val minute = date.minute

    val selectedTimeDate = remember {
        mutableStateOf(Triple(date.date, hour, minute))
    }
    // creates time picker
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            // Update the value
            selectedTimeDate.value = Triple(date.date, hour, minute)
            // inform coroutine about changes
            onItemClick(selectedTimeDate.value)
        }, hour, minute, false
    )

    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .background(color = backgroundColor)
            .clickable {
                if (!isPastDate) {
                        date.isSelected = true
                        timePickerDialog.show()
                }
            },
        contentColor = contentColor,
    ) {
        Column(
            modifier = Modifier
                .width(50.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Text(
                text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1.copy(color = contentColor)
            )
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1.copy(color = contentColor)
            )
        }
    }
}
