package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.viewModels.ViewModelFactory
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class NewLogSheet : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        (application as MyApplication).repository?.let { ViewModelFactory(it) }!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    EditDndLogSheetScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun EditDndLogSheetScreen(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    var logsheet  = viewModel.dndLogSheetBlank

    var playerdcinumber = remember { mutableStateOf("") }
    var charactername = remember { mutableStateOf("") }
    var characterrace = remember { mutableStateOf("") }
    var faction = remember { mutableStateOf("") }
    var classes = remember { mutableStateOf("") }
    if(!viewModel.isNewDndLogSheet){
        logsheet = viewModel.dndLogSheet
         playerdcinumber = remember { mutableStateOf(logsheet.playerDCInumber) }
         charactername = remember { mutableStateOf(logsheet.characterName) }
         characterrace = remember { mutableStateOf(logsheet.characterRace) }
         faction = remember { mutableStateOf(logsheet.faction) }
         classes = remember { mutableStateOf(logsheet.classes) }
    }
    Column(modifier = Modifier.padding(5.dp)
        .fillMaxWidth().verticalScroll(scrollState)){
        TextField(
            value = playerdcinumber.value,
            onValueChange = {
                playerdcinumber.value = it
                viewModel.onPlayerDCInumberChanged(playerdcinumber.value)
            },
            label = { Text(text = stringResource(R.string.playerdcinumber),fontSize = 25.sp,
                color = colorResource(R.color.colorPrimaryDark),
                modifier = Modifier.fillMaxWidth()
            ) }
        )
        TextField(
            value = charactername.value,
            onValueChange = {
                charactername.value = it
                viewModel.onCharacterNameChange(charactername.value)
            },
            label = { Text(text = stringResource(R.string.charactername),fontSize = 25.sp,
                color = colorResource(R.color.colorPrimaryDark),
            modifier = Modifier.fillMaxWidth())}
        )
        TextField(
            value = characterrace.value,
            onValueChange = {
                characterrace.value = it
                viewModel.onCharacterRaceChange(characterrace.value)
            },
            label = { Text(text = stringResource(R.string.characterrace),fontSize = 30.sp,
                color = colorResource(R.color.colorPrimaryDark),
                modifier = Modifier.fillMaxWidth()) }
        )
        TextField(
                value = classes.value,
                onValueChange = {
                    classes.value = it
                    viewModel.onClassChange(classes.value)
                },
                label = { Text(text = stringResource(R.string.startingclass),fontSize = 30.sp,
                    color = colorResource(R.color.colorPrimaryDark),
                    modifier = Modifier.fillMaxWidth()) }
            )
        TextField(
            value = faction.value,
            onValueChange = {
                faction.value = it
                viewModel.onFactionChange(faction.value)
            },
            label = { Text(text = stringResource(R.string.faction),fontSize = 30.sp,
                color = colorResource(R.color.colorPrimaryDark),
                modifier = Modifier.fillMaxWidth()) }
        )

        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onSubmitDndLogSheetButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.submitbutton),
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
                viewModel.onHomeButtonPressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor =
                colorResource(id = R.color.comicred)
                ),
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
    //Text(text = "Hello World! This is edit dnd log sheet screen.")
    BackHandler() {
        viewModel.backButton()
    }
}

@Preview(showBackground = true)
@Composable
fun EditLogSheetScreenPreview() {
    TabletopGamesTheme {

    }
}