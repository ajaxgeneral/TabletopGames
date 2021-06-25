package com.example.tabletopgames.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tabletopgames.R
import com.example.tabletopgames.viewModels.MainViewModel

class MyProfileView: ComponentActivity() {
    var viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyProfileView(viewModel)
        }
    }
}

@Composable
fun MyProfileView(viewModel: MainViewModel) {

    Column(modifier = Modifier.fillMaxSize(1f)
        .padding(5.dp).background(colorResource(R.color.colorAccent))) {
        Text(stringResource(R.string.profile),
                fontSize = 50.sp, fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center, color = colorResource(R.color.comicrose))
        Divider()
        Text(viewModel.myProfile.firstName, fontSize = 25.sp,color = Color.Black)
        Text(viewModel.myProfile.lastName, fontSize = 25.sp,color = Color.Black)
        Text(viewModel.myProfile.email, fontSize = 25.sp,color = Color.Black)
        Text(viewModel.myProfile.phone, fontSize = 25.sp,color = Color.Black)
    }
    //Text(text = "Hello World! It's MyProfile")
    BackHandler() {
        Router.goBack()
    }
}

@Preview(showBackground = true)
@Composable
fun MyProfileViewPreview() {
    MyProfileView(viewModel = MainViewModel())
}