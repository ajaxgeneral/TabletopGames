package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.R
import com.example.tabletopgames.models.*
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class LogSheets : ComponentActivity() {
    val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LogSheetsList(viewModel)
                }
            }
        }
    }
}

@Composable
fun LogSheetsList(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    if (viewModel.logSheetList.isEmpty()){ viewModel.buildLogSheetList() }
    val logsheets = viewModel.logSheetList
    Column(modifier = Modifier.padding(5.dp).fillMaxWidth()
        .verticalScroll(scrollState)){
        logsheets.forEach{ logsheet ->
            val img = viewModel.getImg(logsheet.gameType)
            LogSheetItem(img,logsheet,viewModel,logsheets.indexOf(logsheet))
            Divider()
        }
        Row(modifier = Modifier
            .padding(15.dp).fillMaxWidth(),
            Arrangement.SpaceEvenly
            ){
            TextButton(onClick = {
                viewModel.onNewDndLogSheetButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.d_n_d),
                    color = Color.White
                )
            }
            TextButton(onClick = {
                viewModel.onNewMtgLogSheetButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.m_t_g),
                    color = Color.White
                )
            }
            TextButton(onClick = {
                viewModel.onNewMonopolyLogSheetButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.monopolybutton),
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
    //Text(text = "Hello World! This is the Log sheets View.")
    BackHandler() {
        viewModel.backButton()
    }
}

@Composable
fun LogSheetItem(img: Int, logsheet: LogSheet, viewModel: MainViewModel, index: Int){
    Row(modifier = Modifier.clickable { viewModel.onLogSheetItemClicked(index) }){
        Image(painter = painterResource(img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center)
        Text(text = logsheet.dateCreated,
            fontSize = 30.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LogSheetsList(viewModel = MainViewModel())
}