package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.MyApplication
import com.example.tabletopgames.R
import com.example.tabletopgames.models.MtgEntry
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.viewModels.ViewModelFactory
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class MtgLogSheet : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MtgLogSheetItemView(viewModel)
                }
            }
        }
    }
}

@Composable
fun MtgLogSheetItemView(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    var playersList = remember { viewModel.playersListEmpty }
    var logsheet = remember { viewModel.mtgLogSheetBlank }
    var entries = remember { viewModel.mtgLogSheetEntriesEmpty }
    if(!viewModel.isNewMtgLogSheet){
        logsheet = remember { viewModel.mtgLogSheet }
        playersList = remember { viewModel.playersList }
        if (!viewModel.mtgLogSheetEntries.isNullOrEmpty()){
           entries = viewModel.mtgLogSheetEntries
        }
    }
    var count = 0

    Column(modifier = Modifier
        .padding(5.dp)
        .background(color = colorResource(id = R.color.colorAccent))
        .fillMaxWidth().verticalScroll(scrollState)){
        Text(text = stringResource(R.string.gametype),fontSize = 25.sp,
            color = colorResource(R.color.colorPrimaryDark))
        Text(text = logsheet.gameType)
        Text(text = stringResource(R.string.datecreated),fontSize = 25.sp,
            color = colorResource(R.color.colorPrimaryDark))
        Text(text = logsheet.dayMonthYear)
        Text(text = stringResource(R.string.players),fontSize = 25.sp,
            color = colorResource(R.color.colorPrimaryDark))

        playersList.forEach { player->
            Text(text = player)
        }


        Divider()
        Text(text = stringResource(R.string.games),fontSize = 25.sp,
            color = colorResource(R.color.colorPrimaryDark))
        entries.forEach{ entry->
            count++
            MtgLogSheetItemView(viewModel,entry,entries.indexOf(entry),count)
            Divider()

        }
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onNewMtgLogSheetEntryButtonPressed()
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
                viewModel.onEditMtgLogSheetButtonPressed()
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
                viewModel.onDeleteMtgLogSheetButtonPressed()
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
    //Text("Hello World! This is a Magic Log Sheet Item View.")
    BackHandler() {
        viewModel.backButton()
    }
}
@Composable
fun MtgLogSheetItemView(viewModel: MainViewModel,entry: MtgEntry,index: Int,
        count: Int){

    Column(modifier = Modifier
        .padding(5.dp)
        .border(BorderStroke(1.dp, colorResource(R.color.blue_faded)))
        .fillMaxWidth()
        .clickable { viewModel.onMtgEntryItemClicked(index,count) }){
        Text(text = stringResource(R.string.winner)+" of Game "+count,fontSize = 25.sp,
            color = colorResource(R.color.colorPrimaryDark)
            ,modifier = Modifier.padding(3.dp))
        Text(text = entry.winner,modifier = Modifier.padding(3.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview9() {
    TabletopGamesTheme {

    }
}