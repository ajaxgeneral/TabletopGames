package com.example.tabletopgames.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tabletopgames.R
import com.example.tabletopgames.views.ui.theme.TabletopGamesTheme

@Composable
fun AppDrawer(currentScreen: Screen,
              closeDrawerAction: () -> Unit,
              modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ){
        AppDrawerHeader()
        Divider()
        ScreenNavigationButton(
            icon = Icons.Filled.Home,
            label = stringResource(R.string.home),
            isSelected = currentScreen == Screen.HomeScreen
        ) {
            Router.navigateTo(Screen.HomeScreen)
            closeDrawerAction
        }
        ScreenNavigationButton(
            icon = Icons.Filled.BookOnline,
            label = stringResource(R.string.reservations),
            isSelected = currentScreen == Screen.ReservationsScreen
        ) {
            Router.navigateTo(Screen.ReservationsScreen)
            closeDrawerAction
        }
        ScreenNavigationButton(
            icon = Icons.Filled.LibraryBooks,
            label = stringResource(R.string.logsheets),
            isSelected = currentScreen == Screen.LogSheetsScreen
        ) {
            Router.navigateTo(Screen.LogSheetsScreen)
            closeDrawerAction
        }
    }
}

@Composable
fun ScreenNavigationButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colors

    val imageAlpha = if (isSelected){
        1f
    } else {
        0.6f
    }

    val textColor = if (isSelected){
        colors.primary
    }else{
        colors.onSurface.copy(alpha = 0.6f)
    }

    val backgroundColor = if (isSelected){
        colors.primary.copy(alpha = 0.12f)
    }else{
        colors.surface
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(4.dp)
        ){

            Image(
                imageVector = icon,
                contentDescription = stringResource(R.string.screennavigationbutton),
                colorFilter = ColorFilter.tint(textColor),
                alpha = 1f
            )

            Spacer(Modifier.requiredWidth(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
                color = textColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AppDrawerHeader(){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            imageVector = Icons.Filled.Menu,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            modifier = Modifier
                .padding(16.dp),
            contentDescription = null,
        )

        Text(
            text = stringResource(R.string.menu),
            color = MaterialTheme.colors.primaryVariant
        )


    }

}

@Composable
private fun LightDarkThemeItem(){
    Row(modifier = Modifier.padding(8.dp)) {
        Text(text = stringResource(R.string.themeswitch),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = .6f),
                modifier = Modifier
                    .alpha(1f)
                    .padding(
                        start = 8.dp,
                        top = 8.dp, end = 8.dp, bottom = 8.dp
                    )
                    .align(alignment = Alignment.CenterVertically))


        }
    }


@Preview(showBackground = true)
@Composable
fun ScreenNavigationButtonPreview() {
    TabletopGamesTheme {
        ScreenNavigationButton(
            icon = Icons.Filled.Home,
            label = stringResource(R.string.home),
            isSelected = true){}
    }
}

