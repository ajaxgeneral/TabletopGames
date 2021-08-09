package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tabletopgames.MyApplication
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.viewModels.ViewModelFactory
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class ReservationDetails : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ReservationDetails(viewModel)
                }
            }
        }
    }
}

@Composable
fun ReservationDetails(viewModel: MainViewModel) {
    val reservation = remember { viewModel.reservationsListOf[viewModel.reservationsItemIndex] }
    val painter = remember { viewModel.getImg(reservation.gameType) }
    val contentDescription = remember { reservation.gameType }
    Column(verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally){
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround){
                Image(painterResource(painter),contentDescription)
                Box(){
                    Column(){
                        Row(modifier = Modifier.fillMaxWidth()){
                            Text(text = reservation.gameType)
                        }
                        Row(modifier = Modifier.fillMaxWidth()){
                            Text(text = reservation.dayMonthYear)
                            Text(text = " "+reservation.time)
                        }
                        Row(modifier = Modifier.fillMaxWidth()){
                            Text(text = "Table: "+reservation.gameTable)
                            Text(text = " Seat: "+reservation.seat)
                        }
                        Row(modifier = Modifier.fillMaxWidth()){
                            Text(text = reservation.duration+" Hours")
                        }
                    }
                }

            }

        }
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onEditReservationButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.editreservation),
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
            TextButton(onClick = {
                viewModel.onDeleteReservationButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.deletereservation),
                    color = Color.White
                )
            }
        }
    }

    //Text(text = "Hello World! This is the Reservation Details View")
    BackHandler {
        viewModel.backButton()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TabletopGamesTheme {

    }
}