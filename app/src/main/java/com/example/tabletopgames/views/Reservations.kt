package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.MyApplication
import com.example.tabletopgames.R
import com.example.tabletopgames.models.Reservation
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.viewModels.ViewModelFactory
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class Reservations : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        (application as MyApplication).repository?.let { ViewModelFactory(it) }!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {

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

    val reservations = rememberSaveable() { viewModel.reservationsListOf }

    Column(modifier = Modifier.fillMaxWidth()
            .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally ) {
         reservations.forEach { reservation ->
            val img = viewModel.getImg(reservation.gameType)
            ListItem(img,reservation,viewModel,reservations.indexOf(reservation))
            Divider()
        }
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onNewReservationButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.newreservationbutton),
                    color = Color.White
                )
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
                Text(text = reservation.gameType,
                    fontSize = 25.sp, fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center, color = colorResource(R.color.comicrose))
                Divider()
                Row(){
                    Text(text = reservation.dayMonthYear, fontSize = 18.sp,color = Color.Black)
                    Text(text = " at "+ reservation.time, fontSize = 18.sp,color = Color.Black)
                }

            }
        }
}



@Preview(showBackground = true)
@Composable
fun ReservationsScreenPreview() {

}