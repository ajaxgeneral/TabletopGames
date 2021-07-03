package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
    Text("Hello World! This is the edit mtg log sheet screen.")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    TabletopGamesTheme {
        EditMtgLogSheetScreen(viewModel = MainViewModel())
    }
}