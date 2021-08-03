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
        (application as MyApplication).repository?.let { ViewModelFactory(it) }!!
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
    var logsheet = remember { viewModel.mtgLogSheetBlank }
    if(viewModel.mtgLogSheetIndex != -1){
        logsheet = viewModel.mtgLogSheets[viewModel.mtgLogSheetIndex]
    }
    val entries = remember { viewModel.mtgLogSheetEntries }
    val playersList = remember { logsheet.players.split(";") }
    var count = remember {0}

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
            MtgLogSheetItemView(viewModel,entry,entries.indexOf(entry),count)
            Divider()
            count++
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
        .padding(5.dp).fillMaxWidth()
        .clickable { viewModel.onMtgEntryItemClicked(index,count) }){
        Text(text = stringResource(R.string.winner)+" of Game "+count,fontSize = 25.sp,
            color = colorResource(R.color.colorPrimaryDark))
        Text(text = entry.winner)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview9() {
    TabletopGamesTheme {

    }
}