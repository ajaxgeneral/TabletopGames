package com.example.tabletopgames


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tabletopgames.viewModels.MainViewModel
import com.example.tabletopgames.views.*
import com.example.tabletopgames.views.Router.currentScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun TabletopGamesApp(viewModel: MainViewModel){
    TabletopGamesTheme {
        AppContent(viewModel)
    }


}

@ExperimentalComposeUiApi
@Composable
private fun AppContent(viewModel: MainViewModel){
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    Crossfade(currentScreen,modifier = Modifier.fillMaxWidth()) {
            screenState: MutableState<Screen> ->
        Scaffold(
            topBar = getTopBar(screenState.value,scaffoldState,scope),
            drawerContent = {
                AppDrawer(
                    currentScreen = Screen.HomeScreen,
                    closeDrawerAction = {
                        scope.launch{
                            scaffoldState.drawerState.close()
                        }
                    }
                )
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigationComponent(screenState = screenState)
            },
            content = {
                MainScreenContainer(
                    modifier = Modifier.padding(bottom = 56.dp),
                    screenState = screenState,
                    viewModel = viewModel
                )
                      },
            contentColor = colorResource(id = R.color.colorPrimary),


        )
    }
}

@Composable
fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>) {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf(
        NavigationItem(0, R.drawable.multiverselogo, Screen.HomeScreen),
        NavigationItem(1, R.drawable.reservationbook, Screen.ReservationsScreen),
        NavigationItem(2, R.drawable.profile, Screen.MyProfileScreen),
    )
    BottomNavigation(modifier = modifier) {
        items.forEach {
            BottomNavigationItem(
                icon = {  },
                selected = selectedItem == it.index,
                onClick = {
                    selectedItem = it.index
                    screenState.value = it.screen
                }
            )
        }
    }
}
private data class NavigationItem(val index: Int, val vectorResourceId: Int, val screen: Screen)

fun getTopBar(screenState: Screen, scaffoldState: ScaffoldState,scope: CoroutineScope): @Composable (() -> Unit){
    if(screenState == Screen.MyProfileScreen){
        return {}
    } else {
        return { TopAppBar(scaffoldState = scaffoldState,scope = scope) }
    }
}

@Composable
fun TopAppBar(scaffoldState: ScaffoldState,scope: CoroutineScope){
    val colors = MaterialTheme.colors
    val drawerState = scaffoldState.drawerState

    TopAppBar(
        navigationIcon = {
            IconButton(
                content = {
                    Icon(
                        Icons.Default.Menu,
                        tint = Color.White,
                        contentDescription = stringResource(R.string.menu),
                    )
                },
                onClick = {
                    scope.launch {
                        if (drawerState.isClosed)
                            drawerState.open() else drawerState.close()
                    }
                },
            )
        },
        title = {
            Text(
                text = stringResource(currentScreen.value.titleResId),
                color = colors.primaryVariant,
            )
        },
        backgroundColor = colors.surface,
    )
}

@ExperimentalComposeUiApi
@Composable
private fun MainScreenContainer(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>,
    viewModel: MainViewModel
){
    Surface( modifier = modifier,
                color = MaterialTheme.colors.background) {
        when(screenState.value){
            Screen.HomeScreen -> HomeView(viewModel)
            Screen.ReservationsScreen -> ReservationsListView(viewModel)
            Screen.LogSheetsScreen -> LogSheetsView(viewModel)
            Screen.NewLogSheetScreen -> NewLogSheetScreen(viewModel)
            Screen.NewDNDentryScreen -> NewDNDentry(viewModel)
            Screen.NewMTGentryScreen -> NewMTGentry(viewModel)
            Screen.NewMONOPentryScreen -> NewMONOPentry(viewModel)
            Screen.LoginScreen -> LoginView(viewModel)
            Screen.MyProfileScreen -> MyProfileView(viewModel)
            Screen.ReservationDetailsScreen -> ReservationDetails(viewModel = viewModel)
        }
    }
}
