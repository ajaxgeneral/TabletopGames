package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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

class MtgEntry : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                  MtgEntryScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MtgEntryScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier
        .fillMaxWidth()){
        Row(
            modifier = Modifier.fillMaxWidth(),
            content = {
                Text(
                    text = viewModel.mtgLogSheetEntry.winner,
                    fontSize = 20.sp, color = colorResource(R.color.colorPrimaryDark),
                    maxLines = 10
                )
                Text(
                    text = " "+stringResource(R.string.mtgentrywinnertext)+" ",
                    fontSize = 20.sp, color = colorResource(R.color.colorPrimaryDark),
                )
                Text(
                    viewModel.magicGameNumber.toString() + ".",
                    fontSize = 20.sp, color = colorResource(R.color.colorPrimaryDark),
                )
            },
        )
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    viewModel.onEditMtgEntryButtonPressed()
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
                    text = stringResource(id = R.string.editbutton),
                    color = Color.White
                )
            }
            TextButton(
                onClick = {
                    viewModel.onHomeButtonPressed()
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
                    text = stringResource(id = R.string.home),
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
            TextButton(
                onClick = {
                    viewModel.onDeleteMtgEntryButtonPressed()
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
                    text = stringResource(id = R.string.delete),
                    color = Color.White
                )
            }
        }
    }
    //Text("Hello World! This is the Magic the Gatering entry screen.")
       BackHandler { viewModel.backButton() }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    TabletopGamesTheme {

    }
}