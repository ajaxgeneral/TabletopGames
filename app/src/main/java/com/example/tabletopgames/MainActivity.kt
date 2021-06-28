package com.example.tabletopgames


import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
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