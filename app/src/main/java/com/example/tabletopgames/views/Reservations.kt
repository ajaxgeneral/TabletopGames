package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val scrollState = rememberScrollState()
    //MyList(scrollState,viewModel)
    ReservationList(scrollState,viewModel)
    //Text(text = "Hello World! It's the Reservations.")
    BackHandler() {
        viewModel.backButton()
    }
}

@Composable
fun ReservationList(scrollState: ScrollState, viewModel: MainViewModel) {
    if(viewModel.reservationsListOf.isEmpty())viewModel.buildReservationList()
    val reservations = viewModel.reservationsListOf
    /*
    LazyColumn{
        items(reservations) { reservation ->
            val img = viewModel.getImg(reservation.gameType)
            ListItem(img,reservation,viewModel)
            Divider()
        }
    }

     */

    Column(modifier = Modifier.fillMaxWidth()
            .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally ) {
         reservations.forEach { reservation ->
            val img = viewModel.getImg(reservation.gameType)
            ListItem(img,reservation,viewModel,reservations.indexOf(reservation))
            Divider()
        }
        TextButton(onClick = {
            viewModel.onHomeButtonPressed()
        },
            colors = ButtonDefaults.buttonColors(backgroundColor =
            colorResource(id = R.color.comicred)),
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

@Composable
fun ListItem(img: Int, reservation: Reservation, viewModel: MainViewModel,
            index: Int) {
        Row(modifier = Modifier.clickable { viewModel.onReservationListItemClicked(index) }){
            Image(painter = painterResource(id = img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Column(modifier = Modifier.fillMaxWidth()) {
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