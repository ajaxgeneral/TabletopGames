package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class NewMTGentry() : ComponentActivity() {
    val viewModel = MainViewModel()
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
    var mtgentry = remember { viewModel.mtgLogSheetEntryBlank }
    if (viewModel.mtgEntryItemIndex!=-1){
        mtgentry = viewModel.mtgLogSheetEntries[viewModel.mtgEntryItemIndex]
    }
    val winner = remember { mutableStateOf(mtgentry.winner) }
    Column(modifier = Modifier
        .padding(5.dp).background(color = colorResource(id = R.color.colorAccent))
        .fillMaxWidth()){
        Row(modifier = Modifier.fillMaxWidth()){
            TextField(
                value = winner.value,
                onValueChange = {
                    winner.value = it
                    viewModel.onWinnerChange(winner.value)
                },
                label = { stringResource(R.string.winner) },
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