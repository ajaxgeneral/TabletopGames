package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tabletopgames.R
import com.example.tabletopgames.models.Repository
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

class Login : ComponentActivity() {
    val viewModel = MainViewModel()
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            LoginView(viewModel)
        }
    }


}

@ExperimentalComposeUiApi
@Composable
fun LoginView(viewModel: MainViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = stringResource(id = R.string.email),
            modifier = Modifier.fillMaxWidth())
        MyEmailTextField(viewModel)
        Text(text = stringResource(id = R.string.password_text_view_label),
            modifier = Modifier.fillMaxWidth())
        MyPasswordTextField(viewModel)
        MyButtonRow(viewModel)
        MyLogo(viewModel)
    }
    BackHandler() {
        Router.goBack()
    }
}

@Composable
fun MyLogo(viewModel: MainViewModel) {

}


@ExperimentalComposeUiApi
@Composable
fun MyButtonRow(viewModel: MainViewModel) {
    Row(modifier = Modifier.fillMaxWidth(),
        Arrangement.SpaceEvenly) {
        TextButton(onClick = {
            viewModel.onLoginPressed()
        },
            colors = ButtonDefaults.buttonColors(backgroundColor =
            colorResource(id = R.color.comicred)),
            border = BorderStroke(
                1.dp,color = colorResource(id = R.color.comicrose)
            ),
            modifier = Modifier
        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = Color.White
            )
        }
        TextButton(onClick = {
            viewModel.onProfilePressed()
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.comicred)),
            border = BorderStroke(
                1.dp,color = colorResource(id = R.color.comicrose)
            ),
            modifier = Modifier
        ) {
            Text(
                text = stringResource(id = R.string.createprofilebutton),
                color = Color.White
            )
        }
    }
}



@Composable
fun MyPasswordTextField(viewModel: MainViewModel) {

    val query = remember{ mutableStateOf("") }

    TextField(
        value = query.value,
        onValueChange = {
            query.value = it
            viewModel.onPasswordChange(query.value)
        },
        label = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MyEmailTextField(viewModel: MainViewModel) {

    val query = remember{ mutableStateOf("")}

    TextField(
        value = query.value,
        onValueChange = {
            query.value = it
            viewModel.onEmailChange(query.value)
        },
        label = {},
        modifier = Modifier.fillMaxWidth()
    )
}


@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    LoginView(viewModel = MainViewModel())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {

}