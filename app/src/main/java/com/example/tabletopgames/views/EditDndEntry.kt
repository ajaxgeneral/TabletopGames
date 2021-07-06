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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val advname = remember { mutableStateOf(dndentry.adventureName) }
    val advcode = remember { mutableStateOf(dndentry.adventureCode) }
    val dayMonthYear = remember { mutableStateOf(dndentry.dayMonthYear) }
    val dmdcinumber = remember { mutableStateOf(dndentry.dmDCInumber) }
    val goldplusminus = remember { mutableStateOf(dndentry.goldPlusMinus) }
    val downtimeplusminus = remember { mutableStateOf(dndentry.downtimePlusMinus) }
    val permanentmagicitemsplusminus = remember { mutableStateOf(dndentry.permanentMagicItemsPlusMinus) }
    val newclass = remember { mutableStateOf(dndentry.newClassLevel) }
    val advnotes = remember { mutableStateOf(dndentry.adventureNotes) }
    val levelaccepted = remember { mutableStateOf(dndentry.levelAccepted) }

    Column(modifier = Modifier
        .padding(5.dp).background(color = colorResource(id = R.color.colorAccent))
        .fillMaxWidth().verticalScroll(scrollState)){
        TextField(
            value = advname.value,
            onValueChange = { advname.value = it
                viewModel.onAdvNameChange(advname.value)
            },
            label = {
                Text( text = stringResource(R.string.adventurename),
                fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = advcode.value,
            onValueChange = { advcode.value = it
                viewModel.onAdvNameChange(advcode.value)
            },
            label = {
                Text( text = stringResource(R.string.adventurecode),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = dayMonthYear.value,
            onValueChange = { dayMonthYear.value = it
                viewModel.onAdvNameChange(dayMonthYear.value)
            },
            label = {
                Text( text = stringResource(R.string.daymonthyear),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = dmdcinumber.value,
            onValueChange = { dmdcinumber.value = it
                viewModel.onAdvNameChange(dmdcinumber.value)
            },
            label = {
                Text( text = stringResource(R.string.dmdcinumber),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = viewModel.getStartingLevel(dndentry.logsheetID),
            onValueChange = {
            },
            label = {
                Text( text = stringResource(R.string.startinglevel),
                fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = viewModel.getStartingGold(dndentry.logsheetID),
            onValueChange = {
            },
            label = {
                Text( text = stringResource(R.string.startinggold),
                fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = viewModel.getStartingDowntime(dndentry.logsheetID),
            onValueChange = {
            },
            label = {
                Text( text = stringResource(R.string.startingdowntime),
                fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = viewModel.getStartingMagicItems(dndentry.logsheetID),
            onValueChange = {
            },
            label = {
                Text( text = stringResource(R.string.startingmagicitems),
                fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )

        TextField(
            value = levelaccepted.value,
            onValueChange = { levelaccepted.value = it
                viewModel.onAdvNameChange(levelaccepted.value)
            },
            label = {
                Text( text = stringResource(R.string.levelaccepted),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = goldplusminus.value,
            onValueChange = { goldplusminus.value = it
                viewModel.onAdvNameChange(goldplusminus.value)
            },
            label = {
                Text( text = stringResource(R.string.goldplusminus),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = downtimeplusminus.value,
            onValueChange = { downtimeplusminus.value = it
                viewModel.onAdvNameChange(downtimeplusminus.value)
            },
            label = {
                Text( text = stringResource(R.string.downtimeplusminus),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = permanentmagicitemsplusminus.value,
            onValueChange = { permanentmagicitemsplusminus.value = it
                viewModel.onAdvNameChange(permanentmagicitemsplusminus.value)
            },
            label = {
                Text( text = stringResource(R.string.permanentmagicitemsplusminus),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        TextField(
            value = newclass.value,
            onValueChange = { newclass.value = it
                viewModel.onAdvNameChange(newclass.value)
            },
            label = {
                Text( text = stringResource(R.string.newclass),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )

        TextField(
            value = advnotes.value,
            onValueChange = { advnotes.value = it
                viewModel.onAdvNameChange(advnotes.value)
            },
            label = {
                Text( text = stringResource(R.string.adventurenotes),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.colorPrimaryDark),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onSubmitDndEntryButtonPressed()
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