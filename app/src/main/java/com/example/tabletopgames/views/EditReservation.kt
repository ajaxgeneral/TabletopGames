package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tabletopgames.MyApplication
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.viewModels.ViewModelFactory
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class NewReservation : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NewReservation(viewModel)
                }
            }
        }
    }
}

@Composable
fun NewReservation(viewModel: MainViewModel){
    val scrollState: ScrollState = rememberScrollState()
    var gametypeq = remember { mutableStateOf("") }
    var dayq = remember { mutableStateOf("") }
    var monthq = remember { mutableStateOf("") }
    var timeq = remember { mutableStateOf("") }
    var tableq = remember { mutableStateOf("")}
    var seatq = remember { mutableStateOf("") }
    var durationq = remember { mutableStateOf("") }
    var reserv = viewModel.reservationBlank
    if (!viewModel.isNewReservation){
        reserv = viewModel.reservation
         gametypeq = remember { mutableStateOf(reserv.gameType) }
         dayq = remember { mutableStateOf(reserv.dayMonthYear.substringBefore(" ")) }
         monthq = remember { mutableStateOf(reserv.dayMonthYear.substringAfter(" ")
             .substringBefore(" ")) }
         timeq = remember { mutableStateOf(reserv.time) }
         tableq = remember { mutableStateOf(reserv.gameTable)}
         seatq = remember { mutableStateOf(reserv.seat) }
         durationq = remember { mutableStateOf(reserv.duration) }
    }

    Column(modifier = Modifier.fillMaxWidth(1f).fillMaxSize(1f)
        .verticalScroll(scrollState)){
        Text(stringResource(R.string.gametobeplayed))
       TextField(
           value = gametypeq.value,
           onValueChange = {
               gametypeq.value = it
               viewModel.onGameTypeChange(gametypeq.value)
           },
           label = {  },
           modifier = Modifier.fillMaxWidth()
       )
        Divider()
        Text(stringResource(R.string.day1to31) )
        TextField(
            value = dayq.value,
            onValueChange = {
                dayq.value = it
                viewModel.onDayChange(dayq.value)
            },
            label = {  },
            modifier = Modifier.fillMaxWidth()
        )
        Divider()
        Text(stringResource(R.string.month1to12))
        TextField(
            value = monthq.value,
            onValueChange = {
                monthq.value = it
                viewModel.onMonthChange(monthq.value)
            },
            label = {  },
            modifier = Modifier.fillMaxWidth()
        )
        Divider()
        Text(stringResource(R.string.time1to12AMPM))
        TextField(
            value = timeq.value,
            onValueChange = {
                timeq.value = it
                viewModel.onTimeChange(timeq.value)
            },
            label = {  },
            modifier = Modifier.fillMaxWidth()
        )
        Divider()
        Text(stringResource(R.string.table1to3))
        TextField(
            value = tableq.value,
            onValueChange = {
                tableq.value = it
                viewModel.onTableChange(tableq.value)
            },
            label = {  },
            modifier = Modifier.fillMaxWidth()
        )
        Divider()
        Text(stringResource(R.string.seat1to10) )
        TextField(
            value = seatq.value,
            onValueChange = {
                seatq.value = it
                viewModel.onSeatChange(seatq.value)
            },
            label = {  },
            modifier = Modifier.fillMaxWidth()
        )
        Divider()
        Text(stringResource(R.string.duration1to4))
        TextField(
            value = durationq.value,
            onValueChange = {
                durationq.value = it
                viewModel.onDurationChange(durationq.value)
            },
            label = {  },
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onSubmitReservationButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.submitreservation),
                    color = Color.White
                )
            }
            TextButton(onClick = {
                viewModel.onHomeButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)
                ),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.home),
                    color = Color.White
                )
            }
        }
    }

    //Text("Hello World! This is a New Reservation.")
    BackHandler {
        viewModel.backButton()
    }
}

@Preview(showBackground = true)
@Composable
fun NewReservationPreview8() {
    TabletopGamesTheme {

    }
}