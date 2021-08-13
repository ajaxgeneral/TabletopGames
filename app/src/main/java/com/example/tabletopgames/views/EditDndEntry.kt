package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.MyApplication
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.viewModels.ViewModelFactory
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class NewDNDentry : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        (application as MyApplication).repository?.let { ViewModelFactory(it) }!!
    }
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
    var dndentry = remember { viewModel.dndLogSheetEntryBlank }
    var advname = remember { mutableStateOf("") }
    var advcode = remember { mutableStateOf("") }
    var dmdcinumber = remember { mutableStateOf("") }
    var goldplusminus = remember { mutableStateOf("") }
    var downtimeplusminus = remember { mutableStateOf("") }
    var permanentmagicitemsplusminus = remember { mutableStateOf("") }
    var newclass = remember { mutableStateOf("") }
    var advnotes = remember { mutableStateOf("") }
    var levelaccepted = remember { mutableStateOf("") }
    if (!viewModel.isNewDndEntry ){
        dndentry = viewModel.dndLogSheetEntries[viewModel.dndEntryItemIndex]
        advname = remember { mutableStateOf(dndentry.adventureName) }
        advcode = remember { mutableStateOf(dndentry.adventureCode) }
        dmdcinumber = remember { mutableStateOf(dndentry.dmDCInumber) }
        goldplusminus = remember { mutableStateOf(dndentry.goldPlusMinus) }
        downtimeplusminus = remember { mutableStateOf(dndentry.downtimePlusMinus) }
        permanentmagicitemsplusminus = remember { mutableStateOf(dndentry.permanentMagicItemsPlusMinus) }
        newclass = remember { mutableStateOf(dndentry.newClassLevel) }
        advnotes = remember { mutableStateOf(dndentry.adventureNotes) }
        levelaccepted = remember { mutableStateOf(dndentry.levelAccepted) }
    }

    Column(modifier = Modifier
        .padding(5.dp,bottom = 30.dp).background(color = colorResource(id = R.color.colorAccent))
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
                viewModel.onAdvCodeChange(advcode.value)
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
            value = dmdcinumber.value,
            onValueChange = { dmdcinumber.value = it
                viewModel.onDmDciNumberChange(dmdcinumber.value)
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
        if (!viewModel.isNewDndEntry){
            TextField(
                value = dndentry.startingLevel,
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
                value = dndentry.startingGold,
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
                value = dndentry.startingDowntime,
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
                value = dndentry.startingPermanentMagicItems,
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
        }
        TextField(
            value = goldplusminus.value,
            onValueChange = { goldplusminus.value = it
                viewModel.onGoldPlusMinusChange(goldplusminus.value)
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
                viewModel.onDowntimePlusMinusChange(downtimeplusminus.value)
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
                viewModel.onPermanentMagicItemsPlusMinusChange(permanentmagicitemsplusminus.value)
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
                viewModel.onNewClassChange(newclass.value)
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
                viewModel.onAdvNotesChange(advnotes.value)
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
    //Text(text = "Hello World! This is the edit dnd entry view.")
    BackHandler() {
        viewModel.backButton()
    }
}

@Preview(showBackground = true)
@Composable
fun EditDNDentryScreenPreview() {
    TabletopGamesTheme {

    }
}