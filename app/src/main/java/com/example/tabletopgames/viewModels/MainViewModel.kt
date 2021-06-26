package com.example.tabletopgames.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopgames.R
import com.example.tabletopgames.models.GameType
import com.example.tabletopgames.models.LoginModel
import com.example.tabletopgames.models.Profile
import com.example.tabletopgames.models.Reservation
import com.example.tabletopgames.views.Router
import com.example.tabletopgames.views.Screen
import io.realm.Realm
import java.util.*

open class MainViewModel() : ViewModel() {
    // Realm Stuff
    val realm: Realm by lazy{
        Realm.getDefaultInstance()
    }
    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

    // helper
    var itemIndex = 0
    fun getImg(gameType: String): Int {
        return when (gameType) {
            GameType().DND -> R.drawable.dungeonsndragons
            GameType().MTG -> R.drawable.magicthegathering
            GameType().MONOP -> R.drawable.monopoly
            else -> R.drawable.multiverselogo
        }
    }

    //profile stuff
    private var p =  Profile("1","Test","Profile",
                "test@gmail.com","8178675309")
    var myProfile = p

    fun getProfile(): Profile {
        return myProfile
    }
    fun onEditProfilePressed(){
        Router.navigateTo(Screen.EditProfileScreen)
    }
    fun onCreateProfilePressed(){
        myLogin = LoginModel("","","Password")
        myProfile = Profile("","First Name",
            "Last Name","Email","Phone")
        Router.navigateTo(Screen.EditProfileScreen)
    }
    fun onSubmitProfilePressed(){
        //add new if creating profile
        //update this if editing profile
        myLogin.email = email.value.toString()
        myLogin.password = password.value.toString()
        myProfile.firstName = firstName.value.toString()
        myProfile.lastName = lastName.value.toString()
        myProfile.email = email.value.toString()
        myProfile.phone = phone.value.toString()
        Router.navigateTo(Screen.MyProfileScreen)
    }
    private val _firstName = MutableLiveData("")
    val firstName: LiveData<String> = _firstName
    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> = _lastName
    private val _phone = MutableLiveData("")
    val phone: LiveData<String> = _phone

    fun onPhoneChange(newPhone: String){
        _phone.value = newPhone
    }
    fun onFirstNameChange(newEmail: String){
        _firstName.value = newEmail
    }
    fun onLastNameChange(newPassword: String){
        _lastName.value = newPassword
    }


    //reservation stuff

    fun onReservationListItemClicked(index: Int) {
        itemIndex = index
        Router.navigateTo(Screen.ReservationDetailsScreen)
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

    //login stuff
    private var l = LoginModel("1","test@gmail.com","password")
    var myLogin = l
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
        var newProfile = p
        var foundLogin = l
        var found = false
        //search Realm for email/password
        //goto create profile if not found

        //foundLogin = login realm where email == email
        // newProfile = profile realm where email == Profile.email

            found=true//for testing

        if (found) {
            myProfile = newProfile
            myLogin = foundLogin
            Router.navigateTo(Screen.HomeScreen)
        }
        else {
            Router.navigateTo(Screen.EditProfileScreen)
        }
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

    //generic navigation functions
    fun backButton(){
        Router.goBack()
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

    fun onHomeButtonPressed(){
        Router.navigateTo(Screen.HomeScreen)
    }





}