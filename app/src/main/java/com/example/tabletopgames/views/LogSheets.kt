package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val logsheets = viewModel.logSheetList
    Column(modifier = Modifier.padding(5.dp).fillMaxWidth()
        .verticalScroll(scrollState)){
        logsheets.forEach{ logsheet ->
            val img = viewModel.getImg(logsheet.gameType)
            LogSheetItem(img,logsheet,viewModel,logsheets.indexOf(logsheet))
            Divider()
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