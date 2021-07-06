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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class DndEntry : ComponentActivity() {
    val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DndEntryScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun DndEntryScreen(viewModel: MainViewModel){
    val scrollState = rememberScrollState()

    val entry = viewModel.dndLogSheetEntries[viewModel.dndEntryItemIndex]
    Column(modifier = Modifier
        .padding(5.dp)
        .background(color = colorResource(id = R.color.colorAccent))
        .fillMaxWidth().verticalScroll(scrollState)) {
        Row(modifier = Modifier.fillMaxWidth(1f)) {
            Text( text = stringResource(R.string.adventurename),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.adventureName,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.adventurecode),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.adventureCode,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.daymonthyear),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.dayMonthYear,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.dmdcinumber),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.dmDCInumber,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.startinglevel),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.startingLevel,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.startinggold),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.startingGold,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.startingdowntime),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.startingDowntime,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.startingmagicitems),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.startingPermanentMagicItems,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.levelaccepted),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.levelAccepted,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.goldplusminus),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.goldPlusMinus,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.downtimeplusminus),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.downtimePlusMinus,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.permanentmagicitemsplusminus),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.permanentMagicItemsPlusMinus,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.newclass),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.newClassLevel,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.goldtotal),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(viewModel.getTotalGold(entry.startingGold,
                entry.goldPlusMinus),modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.downtimetotal),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(viewModel.getTotalDowntime(entry.startingDowntime,
                entry.downtimePlusMinus),modifier = Modifier.fillMaxWidth(.5f))
        }
        Row() {
            Text( text = stringResource(R.string.permanentmagicitemstotal),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(viewModel.getTotalPermanentMagicItems(entry.startingPermanentMagicItems,
                entry.permanentMagicItemsPlusMinus),modifier = Modifier.fillMaxWidth(.5f))
        }
        Row(){
            Text( text = stringResource(R.string.adventurenotes),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.colorPrimaryDark),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(.5f)
            )
            Text(entry.adventureNotes,modifier = Modifier.fillMaxWidth(.5f))
        }
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    viewModel.onEditDndEntryButtonPressed()
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
                    text = stringResource(id = R.string.editbutton),
                    color = Color.White
                )
            }
            TextButton(
                onClick = {
                    viewModel.onHomeButtonPressed()
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
                    text = stringResource(id = R.string.home),
                    color = Color.White
                )
            }
            TextButton(
                onClick = {
                    viewModel.onDeleteDndEntryButtonPressed()
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
                    text = stringResource(id = R.string.delete),
                    color = Color.White
                )
            }
        }
    }
    //Text(text = "Hello World! This the Dnd Entry View.")
    BackHandler { viewModel.backButton() }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview11() {
    TabletopGamesTheme {
        DndEntryScreen(viewModel = MainViewModel())
    }
}