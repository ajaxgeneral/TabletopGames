package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class NewDNDentry : ComponentActivity() {
    val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    EditDNDentryScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun EditDNDentryScreen(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    var dndentry = viewModel.dndLogSheetEntries[viewModel.dndEntryItemIndex]
    if (viewModel.dndEntryItemIndex==-1){ dndentry = viewModel.dndLogSheetEntryBlank }

    Column(modifier = Modifier
        .padding(5.dp).background(color = colorResource(id = R.color.colorAccent))
        .fillMaxWidth().verticalScroll(scrollState)){

    }
    //Text(text = "Hello World! This is the edit dnd entry view.")
    BackHandler() {
        viewModel.backButton()
    }
}

@Preview(showBackground = true)
@Composable
fun EditDNDentryScreenPreview() {
    TabletopGamesTheme {
        EditDNDentryScreen(viewModel = MainViewModel())
    }
}