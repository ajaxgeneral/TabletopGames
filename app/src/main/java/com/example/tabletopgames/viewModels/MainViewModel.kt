package com.example.tabletopgames.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopgames.models.LoginModel
import com.example.tabletopgames.views.Router
import com.example.tabletopgames.views.Screen
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class MainViewModel() : ViewModel() {

    val realm: Realm by lazy{
        Realm.getDefaultInstance()
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