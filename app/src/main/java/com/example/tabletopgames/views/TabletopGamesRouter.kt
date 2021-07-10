package com.example.tabletopgames.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.tabletopgames.R

sealed class Screen(val titleResId: Int){
    object LoginScreen: Screen(R.string.login)
    object HomeScreen: Screen(R.string.home)
    object ReservationsScreen: Screen(R.string.reservations)
    object LogSheetsScreen: Screen(R.string.logsheets)
    object EditDndLogSheetScreen: Screen(R.string.editdndlogsheet)
    object EditDNDentryScreen: Screen(R.string.editdndentry)
    object EditMtgLogSheetScreen: Screen(R.string.editmtglogsheeet)
    object EditMTGentryScreen: Screen(R.string.editmtgentry)
    object EditMonopLogSheetScreen: Screen(R.string.editmonoplogsheet)
    object EditMONOPentryScreen: Screen(R.string.editmonopentry)
    object MyProfileScreen: Screen(R.string.profile)
    object ReservationDetailsScreen: Screen(R.string.reservationdetails)
    object NewReservationScreen: Screen(R.string.newreservation)
    object EditProfileScreen: Screen(R.string.editprofile)
    object DndLogSheetScreen: Screen(R.string.title_activity_dnd_log_sheet)
    object MtgLogSheetScreen: Screen(R.string.magiclogsheet)
    object MonopolyLogSheetScreen: Screen(R.string.monopolylogsheet)
    object DndEntryScreen: Screen(R.string.dndentry)
    object MtgEntryScreen: Screen(R.string.mtgentry)
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