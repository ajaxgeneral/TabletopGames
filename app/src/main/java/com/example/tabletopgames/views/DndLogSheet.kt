package com.example.tabletopgames.views

import android.graphics.fonts.FontStyle
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import com.example.tabletopgames.models.DndAlEntry
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class DndLogSheet : ComponentActivity() {
    val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DndLogSheetItemView(viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DndLogSheetItemView(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val logsheet = viewModel.dndLogSheets[0]
    val entries = viewModel.dndLogSheetEntries
    Column(modifier = Modifier
        .padding(5.dp)
        .background(color = colorResource(id = R.color.colorAccent))
            .fillMaxWidth().verticalScroll(scrollState)){
        Box(modifier = Modifier
            .padding(5.dp).background(color = colorResource(R.color.colorAccent))){
            Column(modifier = Modifier
                .padding(5.dp)
                .background(color = colorResource(R.color.colorAccent))
                .fillMaxWidth()){
                Text(text = stringResource(R.string.playerdcinumber),fontSize = 25.sp,
                    color = colorResource(R.color.colorPrimaryDark))
                Text(logsheet.playerDCInumber,fontSize = 15.sp,
                    color = colorResource(R.color.blue))
                Divider()
                Text(text = stringResource(R.string.charactername),fontSize = 25.sp,
                    color = colorResource(R.color.colorPrimaryDark))
                Text(logsheet.characterName,fontSize = 15.sp,
                    color = colorResource(R.color.blue))
                Divider()
                Text(text = stringResource(R.string.characterrace),fontSize = 30.sp,
                    color = colorResource(R.color.colorPrimaryDark))
                Text(logsheet.characterRace,fontSize = 15.sp,
                    color = colorResource(R.color.blue))
                Divider()
                Text(text = stringResource(R.string.classes),fontSize = 30.sp,
                    color = colorResource(R.color.colorPrimaryDark))
                Text(logsheet.classes,fontSize = 15.sp,
                    color = colorResource(R.color.blue))
                Divider()
                Text(text = stringResource(R.string.faction),fontSize = 30.sp,
                    color = colorResource(R.color.colorPrimaryDark))
                Text(logsheet.faction,fontSize = 15.sp,
                    color = colorResource(R.color.blue))
                Divider()
                Text(text = stringResource(R.string.souldcoinscarried),fontSize = 30.sp,
                    color = colorResource(R.color.colorPrimaryDark))
                Text(logsheet.soulCoinsCarried,fontSize = 15.sp,
                    color = colorResource(R.color.blue))
                Divider()
                Text(text = stringResource(R.string.souldcoinchargesused),fontSize = 30.sp,
                    color = colorResource(R.color.colorPrimaryDark))
                Text(logsheet.soulCoinChargesUsed,fontSize = 15.sp,
                    color = colorResource(R.color.blue))
                Divider()
                Text(text = stringResource(R.string.logsheetentries),fontSize = 25.sp,
                    color = colorResource(R.color.colorPrimaryDark),
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )

            }
        }
        entries.forEach { entry ->
            DndEntryItem(viewModel,entry,entries.indexOf(entry))
            Divider()
        }
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onNewDndEntryButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.newentrybutton),
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
    //Text("Hello World! This is the Dnd Log Sheet Item View.")
    BackHandler() {
        viewModel.backButton()
    }
}

@Composable
fun DndEntryItem(viewModel: MainViewModel,entry: DndAlEntry, index: Int){
    Column(modifier = Modifier
        .padding(5.dp).fillMaxWidth()
        .clickable { viewModel.onDndEntryItemClicked(index) }
    ){
        Text(entry.adventureName)
        Text(entry.dayMonthYear)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview8() {
    TabletopGamesTheme {
        DndLogSheetItemView(viewModel = MainViewModel())
    }
}