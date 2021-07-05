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

class EditMonopLogSheet : ComponentActivity() {
    val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    EditMonopLogSheetScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun EditMonopLogSheetScreen(viewModel: MainViewModel) {
    Text("Hello World! This is the edit monopoly log sheet screen.")
    BackHandler() {
        viewModel.backButton()
    }
}

@Preview(showBackground = true)
@Composable
fun EditMonopLogSheetScreenPreview() {
    TabletopGamesTheme {
        EditMtgLogSheetScreen(viewModel = MainViewModel())
    }
}