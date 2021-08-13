package com.example.tabletopgames.views

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.MyApplication
import com.example.tabletopgames.R
import com.example.tabletopgames.models.DndAlEntry
import com.example.tabletopgames.models.DndAlLogSheet
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.viewModels.ViewModelFactory
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class DndLogSheet : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).repository)
    }
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
    var logsheet = remember { viewModel.dndLogSheetBlank }
    var entries = remember { viewModel.dndLogSheetEntriesEmpty }
    if(!viewModel.isNewDndLogSheet){
        logsheet = remember { viewModel.dndLogSheet }
        if (!viewModel.dndLogSheetEntries.isNullOrEmpty()){
            entries = viewModel.dndLogSheetEntries
        }
    }
/*
    val logsheet = if (viewModel.isNewDndLogSheet){
            remember { viewModel.dndLogSheetBlank }
        } else { remember { viewModel.dndLogSheet } }
    val entries = if (viewModel.dndLogSheetEntries.any()){
            remember { viewModel.dndLogSheetEntries }
        } else { remember { viewModel.dndLogSheetEntriesEmpty } }
*/


    Column(modifier = Modifier
        .padding(5.dp,bottom = 30.dp)
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
                Text(text = stringResource(R.string.logsheetentries),fontSize = 25.sp,
                    color = colorResource(R.color.colorPrimaryDark),
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }

        entries.forEach { entry ->
                DndLogSheetEntryItem(viewModel,entry,entries.indexOf(entry))
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
            TextButton(onClick = {
                viewModel.onEditDndLogSheetButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.edit),
                    color = Color.White
                )
            }
            TextButton(
                onClick = {
                    viewModel.onLogSheetsPressed()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =
                    colorResource(id = R.color.comicred)
                ),
                border = BorderStroke(
                    1.dp, color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.logsheets),
                    color = Color.White
                )
            }
            TextButton(onClick = {
                viewModel.onDeleteDndLogSheetButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
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
fun DndLogSheetEntryItem(viewModel: MainViewModel,entry: DndAlEntry, index: Int){
    Column(modifier = Modifier
        .padding(5.dp)
        .border(BorderStroke(1.dp, colorResource(R.color.blue_faded)))
        .fillMaxWidth()
        .clickable { viewModel.onDndEntryItemClicked(index) }
    ){
        Text(entry.adventureName,modifier = Modifier.padding(3.dp))
        Text(entry.dayMonthYear,modifier = Modifier.padding(3.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview8() {
    TabletopGamesTheme {

    }
}