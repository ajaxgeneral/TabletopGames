package com.example.tabletopgames.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopgames.R
import com.example.tabletopgames.models.GameType
import com.example.tabletopgames.models.LoginModel
import com.example.tabletopgames.models.Reservation
import com.example.tabletopgames.views.Router
import com.example.tabletopgames.views.Screen
import io.realm.Realm
import java.util.*

class MainViewModel() : ViewModel() {

    val realm: Realm by lazy{
        Realm.getDefaultInstance()
    }

    fun getImg(gameType: String): Int {
        return when (gameType) {
            GameType().DND -> R.drawable.dungeonsndragons
            GameType().MTG -> R.drawable.magicthegathering
            GameType().MONOP -> R.drawable.monopoly
            else -> R.drawable.multiverselogo
        }
    }

    val reservationsListOf = mutableListOf<Reservation>()
    fun buildReservationList(){
        reservationsListOf.add(
            Reservation("1","1",GameType().DND,
                "1 January 2021","7:00 PM","1",
                "1","1"))
        reservationsListOf.add(Reservation("2","1",GameType().DND,
            "2 January 2021","6:00 PM","2",
            "2","2"))
        reservationsListOf.add(Reservation("3","1",GameType().MTG,
            "3 January 2021","5:00 PM","3",
            "3","3"))
        reservationsListOf.add(Reservation("4","1",GameType().MONOP,
            "4 January 2021","4:00 PM","4",
            "4","4"))
    }

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    fun onEmailChange(newEmail: String){
        _email.value = newEmail
    }
    fun onPasswordChange(newPassword: String){
        _password.value = newPassword
    }

    fun onLoginPressed(){
        //search Realm for email/password
        //goto create profile if not found
        var found = false
        //var login = realm query function
        //if(login)
            found=true//for testing

        if (found) Router.navigateTo(Screen.HomeScreen)
        else Router.navigateTo(Screen.MyProfileScreen)
    }

    fun onProfilePressed() {
        Router.navigateTo(Screen.MyProfileScreen)
    }

    fun onReservationsPressed() {
        Router.navigateTo(Screen.ReservationsScreen)
    }

    fun onLogSheetsPressed() {
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    fun onListItemClicked(){
        Router.navigateTo(Screen.HomeScreen)
    }

    private fun addNewLogin(email: String,password: String) {

        // create a Login object
        val id = Calendar.getInstance().timeInMillis.toString() +
        " " + UUID.randomUUID().toString()
        val newLogin = LoginModel(id,email, password)

        // on below line we are calling a method to execute a transaction.
            realm.executeTransactionAsync { realm -> // inside on execute method we are calling a method
                // to copy to realm database from our modal class.
                realm.copyToRealm(newLogin)
            }

    }




}