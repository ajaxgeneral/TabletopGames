package com.example.tabletopgames


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.example.tabletopgames.MyApplication
import com.example.tabletopgames.TabletopGamesApp
import com.example.tabletopgames.models.Repository
import com.example.tabletopgames.viewModels.LogSheetsViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme
import com.example.tabletopgames.viewModels.MainViewModel

class MainActivity : ComponentActivity() {
//    val application = MyApplication()
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            TabletopGamesApp(viewModel = MainViewModel())
        }
    }


}