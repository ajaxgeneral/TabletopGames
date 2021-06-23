package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tabletopgames.models.Reservation
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class Reservations : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = MainViewModel()
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ReservationsListView(viewModel)
                }
            }
        }
    }
}

@Composable
fun ReservationsListView(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    MyList(scrollState,viewModel)
    //Text(text = "Hello World! It's the Reservations.")
    BackHandler() {
        Router.goBack()
    }
}

@Composable
fun MyList(scrollState: ScrollState, viewModel: MainViewModel) {
    viewModel.buildReservationList()
    ReservationList(scrollState, viewModel)
}

@Composable
fun ReservationList(scrollState: ScrollState, viewModel: MainViewModel) {
    val reservations = viewModel.reservationsListOf
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        reservations.forEach { reservation ->
            val img = viewModel.getImg(reservation.gameType)
            ListItem(img,reservation,viewModel)
            Divider()
        }
    }
}

@Composable
fun ListItem(img: Int, reservation: Reservation, viewModel: MainViewModel) {
        Row(modifier = Modifier.clickable { viewModel.onListItemClicked() }){
            Image(painter = painterResource(id = img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Column() {
                Text(text = reservation.gameType)
                Divider()
                Row(){
                    Text(text = reservation.dayMonthYear)

                    Text(text = " at "+ reservation.time)
                }

            }
        }
}



@Preview(showBackground = true)
@Composable
fun ReservationsScreenPreview() {
    ReservationsListView(viewModel = MainViewModel())
}