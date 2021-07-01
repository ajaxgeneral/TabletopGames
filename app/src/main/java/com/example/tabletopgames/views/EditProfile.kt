package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class EditProfile : ComponentActivity() {
    val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabletopGamesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    EditProfile(viewModel)
                }
            }
        }
    }
}

@Composable
fun EditProfile(viewModel: MainViewModel) {
    val firstNameq = remember{ mutableStateOf("") }
    val lastNameq = remember{ mutableStateOf("") }
    val emailq = remember{ mutableStateOf("") }
    val phoneq = remember{ mutableStateOf("") }
    val passwordq = remember{ mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(1f)
        .padding(5.dp).background(colorResource(R.color.colorAccent))) {
        Text(
            stringResource(R.string.profile),
            fontSize = 50.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, color = colorResource(R.color.comicrose)
        )
        Divider()
        TextField(
        value = firstNameq.value,
        onValueChange = {
            firstNameq.value = it
            viewModel.onFirstNameChange(firstNameq.value)
        },
        label = { Text(viewModel.myProfile.firstName) },
        modifier = Modifier.fillMaxWidth()
        )
        TextField(
        value = lastNameq.value,
        onValueChange = {
            lastNameq.value = it
            viewModel.onLastNameChange(lastNameq.value)
        },
        label = { Text(viewModel.myProfile.lastName) },
        modifier = Modifier.fillMaxWidth()
        )
        TextField(
        value = emailq.value,
        onValueChange = {
            emailq.value = it
            viewModel.onEmailChange(emailq.value)
        },
        label = { Text(viewModel.myProfile.email) },
        modifier = Modifier.fillMaxWidth()
        )
        TextField(
        value = phoneq.value,
        onValueChange = {
            phoneq.value = it
            viewModel.onPhoneChange(phoneq.value)
        },
        label = { Text(viewModel.myProfile.phone) },
        modifier = Modifier.fillMaxWidth()
        )
        TextField(
        value = passwordq.value,
        onValueChange = {
            passwordq.value = it
            viewModel.onPasswordChange(passwordq.value)
        },
        label = { Text(viewModel.myLogin.password) },
        modifier = Modifier.fillMaxWidth()
    )
        Row(modifier = Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly){
            TextButton(onClick = {
                viewModel.onSubmitProfilePressed()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.comicred)),
                border = BorderStroke(
                    1.dp,color = colorResource(id = R.color.comicrose)
                ),
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(id = R.string.submitprofile),
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
    //Text(text = "Hello World! This is Edit Profile.")
    BackHandler { viewModel.backButton() }
}

@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    TabletopGamesTheme {
        EditProfile(viewModel = MainViewModel())
    }
}