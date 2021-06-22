package com.example.tabletopgames.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.tabletopgames.R

sealed class Screen(val titleResId: Int){
    object LoginScreen: Screen(R.string.login)
    object HomeScreen: Screen(R.string.home)
    object ReservationsScreen: Screen(R.string.reservations)
    object LogSheetsScreen: Screen(R.string.logsheets)
    object NewLogSheetScreen: Screen(R.string.newlogsheet)
    object NewDNDentryScreen: Screen(R.string.newdndentry)
    object NewMTGentryScreen: Screen(R.string.newmtgentry)
    object NewMONOPentryScreen: Screen(R.string.newmonopentry)
    object MyProfileScreen: Screen(R.string.profile)
}

object Router{
    var currentScreen: MutableState<Screen> = mutableStateOf(
        Screen.LoginScreen
    )

    private var previousScreen: MutableState<Screen> = mutableStateOf(
        Screen.HomeScreen
    )

    fun navigateTo(destination: Screen){
        previousScreen.value = currentScreen.value
        currentScreen.value = destination
    }

    fun goBack(){
        currentScreen.value = previousScreen.value
    }
}