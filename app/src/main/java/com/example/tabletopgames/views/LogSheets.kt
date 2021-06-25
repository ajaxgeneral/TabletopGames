package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

                }
            }
        }
    }
}

@Composable
fun LogSheetsView(viewModel: MainViewModel) {
    Text(text = "Hello World! This is the Log sheets View.")
    BackHandler() {
        Router.goBack()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LogSheetsView(viewModel = MainViewModel())
}