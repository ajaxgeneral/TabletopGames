package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.MyApplication
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.viewModels.ViewModelFactory
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class NewMTGentry() : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    EditMTGentryScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun EditMTGentryScreen(viewModel: MainViewModel){
    var mtgentry = viewModel.mtgLogSheetEntryBlank
    var winners = ""
    var winner = rememberSaveable { mutableStateOf("") }
    if (!viewModel.isNewMtgEntry){
        mtgentry = viewModel.mtgLogSheetEntry
        winner = rememberSaveable { mutableStateOf(mtgentry.winner) }
    }

    Column(modifier = Modifier
        .padding(5.dp).background(color = colorResource(id = R.color.colorAccent))
        .fillMaxWidth()){
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = stringResource(R.string.players)+": ",fontSize = 25.sp,
                color = colorResource(R.color.blue))
            viewModel.playersList.forEach{ player ->
                Text(text = player)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = stringResource(R.string.winners)+": ",fontSize = 25.sp,
                color = colorResource(R.color.blue))
            TextField(
                value = winner.value,
                onValueChange = {
                    winner.value = it
                    viewModel.onWinnerChange(winner.value)
                },
                label = { stringResource(R.string.winner) },
                maxLines = 10,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onSubmitMtgEntryButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.submitbutton),
                    color = Color.White
                )
            }
            TextButton(
                onClick = {
                    viewModel.onLogSheetsPressed()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =
                    colorResource(id = R.color.comicred)
                ),
                border = BorderStroke(
                    1.dp, color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.logsheets),
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
    BackHandler() {
        viewModel.backButton()
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {

}