package com.example.tabletopgames.views

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class Home : ComponentActivity() {
    val viewModel = MainViewModel()
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            HomeView(viewModel)
        }
    }
}

@Composable
fun HomeView(viewModel: MainViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.colorAccent))) {
        Image(painter = painterResource(id = R.drawable.multiversemainimage),
            contentDescription = null,
            contentScale = ContentScale.Fit)
        Image(painter = painterResource(id = R.drawable.dnd),
            contentDescription = null,
            contentScale = ContentScale.Fit)

       // Box(modifier = Modifier) {


            Row(modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceEvenly) {
                TextButton(onClick = {
                    viewModel.onReservationsPressed()
                },
                    colors = ButtonDefaults.buttonColors(backgroundColor =
                    colorResource(id = R.color.comicred)),
                    border = BorderStroke(
                        1.dp,color = colorResource(id = R.color.comicrose)
                    ),
                    modifier = Modifier
                ) {
                    Text(
                        text = stringResource(id = R.string.reservations),
                        color = Color.White
                    )
                }
                TextButton(onClick = {
                    viewModel.onLogSheetsPressed()
                },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.comicred)),
                    border = BorderStroke(
                        1.dp,color = colorResource(id = R.color.comicrose)
                    ),
                    modifier = Modifier
                ) {
                    Text(
                        text = stringResource(id = R.string.logsheets),
                        color = Color.White
                    )
                }
                TextButton(onClick = {
                    viewModel.onProfilePressed()
                },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.comicred)),
                    border = BorderStroke(
                        1.dp,color = colorResource(id = R.color.comicrose)
                    ),
                    modifier = Modifier
                ) {
                    Text(
                        text = stringResource(id = R.string.profile),
                        color = Color.White
                    )
                }
            }
        //}
    }
    //Text(text = "Hello World! Its Home view.")
    BackHandler() {
        Router.goBack()
    }

}


@Preview
@Composable
fun HomeViewPreview(){
    HomeView(viewModel = MainViewModel())
}