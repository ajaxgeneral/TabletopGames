package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class EditMtgLogSheet : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = MainViewModel()
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                  EditMtgLogSheetScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun EditMtgLogSheetScreen(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    var logsheet = remember { viewModel.mtgLogSheetBlank }
    var playersList = remember { viewModel.playersListEmpty }
    if(viewModel.mtgLogSheetIndex != -1){
        logsheet = viewModel.mtgLogSheets[viewModel.mtgLogSheetIndex]
        playersList = viewModel.testListOfPlayers
    }
    var count = remember {0}
    val date_created = remember { mutableStateOf(logsheet.dayMonthYear) }
    val newPlayer = remember { mutableStateOf("") }
    var newplayerslist = remember { viewModel.newPlayersList }

    Column(modifier = Modifier
        .padding(5.dp)
        .background(color = colorResource(id = R.color.colorAccent))
        .fillMaxWidth().verticalScroll(scrollState)){

        Text(text = stringResource(R.string.gametype),fontSize = 25.sp,
            color = colorResource(R.color.colorPrimaryDark)
        )
        Text(text = logsheet.gameType)
        if(viewModel.mtgLogSheetIndex != -1){
            Text(text = stringResource(R.string.datecreated),fontSize = 25.sp,
                color = colorResource(R.color.colorPrimaryDark)
            )
            TextField(
                value = date_created.value,
                onValueChange = {
                    date_created.value  = it
                    viewModel.onDateCreatedChange(date_created.value)
                }
            )
        }
        Text(text = stringResource(R.string.players),fontSize = 25.sp,
            color = colorResource(R.color.colorPrimaryDark)
        )
        newplayerslist.forEach { newplayer ->
            Text(newplayer)
        }
        Divider()
        TextField(
            value = newPlayer.value,
            onValueChange = {
                newPlayer.value = it
                viewModel.onPlayerChange(newPlayer.value)}
        )
        Divider()
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(
                onClick = { viewModel.onAddPlayerButtonPressed(newPlayer.value) },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)
                ),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ){
                Text(
                    text = stringResource(id = R.string.addplayerbutton),
                    color = Color.White
                )
            }
            TextButton(
                onClick = { viewModel.onSubmitMtgLogSheetButtonPressed() },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)
                ),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.newentrybutton),
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

    //Text("Hello World! This is the edit mtg log sheet screen.")
    BackHandler() {
        viewModel.backButton()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    TabletopGamesTheme {
        EditMtgLogSheetScreen(viewModel = MainViewModel())
    }
}