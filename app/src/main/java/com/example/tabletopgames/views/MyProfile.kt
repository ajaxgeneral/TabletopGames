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
import com.example.tabletopgames.models.Repository
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class MyProfileView: ComponentActivity() {
    var viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyProfileView(viewModel)
        }
    }
}

@Composable
fun MyProfileView(viewModel: MainViewModel) {
    Text(text = "Hello World! It's MyProfile")
    BackHandler() {
        Router.goBack()
    }
}

@Preview(showBackground = true)
@Composable
fun MyProfileViewPreview() {
    MyProfileView(viewModel = MainViewModel())
}