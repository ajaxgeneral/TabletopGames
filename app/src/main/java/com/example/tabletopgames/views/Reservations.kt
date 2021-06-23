package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tabletopgames.R
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
    MyList(viewModel)
    //Text(text = "Hello World! It's the Reservations.")
    BackHandler() {
        Router.goBack()
    }
}

@Composable
fun MyList(viewModel: MainViewModel) {
    viewModel.buildReservationList()
    ReservationList(viewModel)
}

@Composable
fun ReservationList(viewModel: MainViewModel) {
    val reservations = viewModel.reservationsListOf
    Column {
        reservations.forEach { reservation ->
            val img = viewModel.getImg(reservation.gameType)
            ListItem(img,reservation)
            Divider()
        }
    }
}

@Composable
fun ListItem(img: Int,reservation: Reservation) {
        Row(){
            Image(painter = painterResource(id = img),
                contentDescription = null,
                contentScale = ContentScale.Fit
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