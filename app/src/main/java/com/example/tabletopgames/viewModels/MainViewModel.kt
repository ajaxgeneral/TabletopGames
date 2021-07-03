package com.example.tabletopgames.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopgames.R
import com.example.tabletopgames.models.*
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
    var itemIndex = -1
    fun getImg(gameType: String): Int {
        return when (gameType) {
            GameType().DND -> R.drawable.dungeonsndragons
            GameType().MTG -> R.drawable.magicthegathering
            GameType().MONOP -> R.drawable.monopoly
            else -> R.drawable.multiverselogo
        }
    }
    private fun getMonthName(month: String) : String{
        var monthName = "January"
        monthName = when(month){
            "1" -> "January"
            "2" -> "February"
            "3" -> "March"
            "4" -> "April"
            "5" -> "May"
            "6" -> "June"
            "7" -> "July"
            "8" -> "August"
            "9" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> "Error"
        }
        return monthName
    }
    private fun getNewID(): String {return Calendar.getInstance().timeInMillis.toString() +
                " " + UUID.randomUUID().toString()
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

    //profile stuff
    private var p =  Profile("1","Test","Profile",
                "test@gmail.com","8178675309")
    var myProfile = p


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
        if (email.value.toString() != "")
        myLogin.email = email.value.toString()
        myProfile.email = email.value.toString()
        if(password.value.toString() != "")
        myLogin.password = password.value.toString()
        if(firstName.value.toString() != "")
        myProfile.firstName = firstName.value.toString()
        if (lastName.value.toString() != "")
        myProfile.lastName = lastName.value.toString()
        if (phone.value.toString() != "")
        myProfile.phone = phone.value.toString()
        Router.navigateTo(Screen.MyProfileScreen)
    }

    //reservation stuff
    private val _gametype = MutableLiveData("")
    val gametype: LiveData<String>  = _gametype
    private val _day = MutableLiveData("")
    val day: LiveData<String> = _day
    private val _month = MutableLiveData("")
    val month: LiveData<String> = _month
    private val _time = MutableLiveData("")
    val time: LiveData<String> = _time
    private val _table = MutableLiveData("")
    val table: LiveData<String> = _table
    private val _seat = MutableLiveData("")
    val seat: LiveData<String> = _seat
    private val _duration = MutableLiveData("")
    val duration: LiveData<String> =_duration
    val reservationsListOf = mutableListOf<Reservation>()
    private val newReservation = Reservation("","","",
                "","","","","")
    fun onGameTypeChange(gameType: String){
        _gametype.value = gameType
    }
    fun onDayChange(day: String){
        _day.value = day
    }
    fun onMonthChange(month: String){
        _month.value = month
    }
    fun onTimeChange(time: String){
        _time.value = time
    }
    fun onTableChange(table: String){
        _table.value = table
    }
    fun onSeatChange(seat: String){
        _seat.value = seat
    }
    fun onDurationChange(duration: String){
        _duration.value = duration
    }
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
    private fun getNewResYear(day: String, month: String) : String{
        val cal = Calendar.getInstance()
        var yearV = ""

        val today = cal.get(Calendar.DAY_OF_MONTH)
        val thisMonth = cal.get(Calendar.MONTH)
        val thisYear = cal.get(Calendar.YEAR)
        if (thisMonth==12 && month=="12" && today <= day.toInt()){
            yearV = cal.add(thisYear, 1).toString()
        }
        else {
            yearV = thisYear.toString()
        }
        return yearV
    }
    fun onSubmitReservationButtonPressed(){
        val dayV = day.toString()
        val monthV = month.toString()
        val year = getNewResYear(dayV,monthV)
        val dayMonthYear = dayV + " " + getMonthName(monthV) + " " + year
        if (itemIndex==-1){
            newReservation.gameType = gametype.value.toString()
            newReservation.dayMonthYear = dayMonthYear
            newReservation.table = table.value.toString()
            newReservation.seat = seat.value.toString()
            newReservation.duration = duration.value.toString()
            newReservation.profileID = myProfile.id
            newReservation.id = getNewID()
            //if no reservation with this date, time, and location exists
            //then add to Reservation Realm and add to reservationsListOf

            reservationsListOf.add(newReservation)
        }
        else {
            reservationsListOf[itemIndex].gameType = gametype.value.toString()
            reservationsListOf[itemIndex].dayMonthYear = dayMonthYear
            reservationsListOf[itemIndex].table = table.value.toString()
            reservationsListOf[itemIndex].seat = seat.value.toString()
            reservationsListOf[itemIndex].duration = duration.value.toString()
        }
        Router.navigateTo(Screen.ReservationsScreen)
    }
    fun onEditReservationButtonPressed(){
        Router.navigateTo(Screen.NewReservationScreen)
    }
    fun onNewReservationButtonPressed(){
        itemIndex = -1
        Router.navigateTo(Screen.NewReservationScreen)
    }
    fun onDeleteReservationButtonPressed(){
        //delete reservationsListOf[itemIndex]
        reservationsListOf.removeAt(itemIndex)
        //remove It from Realm

        Router.navigateTo(Screen.ReservationsScreen)
    }
    fun onReservationListItemClicked(index: Int) {
        itemIndex = index
        Router.navigateTo(Screen.ReservationDetailsScreen)
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
        val newLogin = LoginModel(getNewID(),email, password)

        // on below line we are calling a method to execute a transaction.
        realm.executeTransactionAsync { realm -> // inside on execute method we are calling a method
            // to copy to realm database from our modal class.
            realm.copyToRealm(newLogin)
        }

    }

    // log sheet stuff

    // getAllLogSheets from Realm where profileID == myProfile.id
    // getAllDndLogSheets from Realm where profileID == myProfile.id
    // for each DndLogSheet getAllDndEntries from Realm where logsheetID == dndAlLogSheet.id
    // getAllMtgLogSheets from Realm where profileID == myProfile.id
    // for each MtgLogSheet getAllMtgEntries from Realm where logsheetID == mtgLogSheet.id
    // getAllMonopLogSheets from Realm where profileID == myProfile.id
    // for each monopLogSheet getAllMonopEntries from Realm where logsheetID == monopLogSheet.id

    //These LogSheet items are created from all log sheet items.
    //the LogSheet.id == DndAlLogSheet.id or MtgLogSheet.id or MonopLogSheet.id

    // use RealmResults to build this list.
    var logSheetList: List<LogSheet> = listOf(
        LogSheet("1",myProfile.id,GameType().DND,"1 January 2021"),
        LogSheet("2",myProfile.id,GameType().MTG,"2 January 2021"),
        LogSheet("3",myProfile.id,GameType().MONOP,"3 January 2021")
    )
    var logSheetItem = LogSheet("","","","")
    var logsheetItemIndex = -1

    fun onLogSheetItemClicked(index: Int){
        logSheetItem = logSheetList[index]
        logsheetItemIndex = index
        when(logSheetItem.gameType){
            GameType().DND -> Router.navigateTo(Screen.DndLogSheetScreen)
            GameType().MTG -> Router.navigateTo(Screen.MtgLogSheetScreen)
            GameType().MONOP -> Router.navigateTo(Screen.MonopolyLogSheetScreen)
            else -> Router.navigateTo(Screen.HomeScreen)
        }
    }

    var dndLogSheet = DndAlLogSheet("","",GameType().DND,"",
        "","","","","","none",
        "")

    // use RealmResults to build this list
    var dndLogSheets: List<DndAlLogSheet> = listOf(
        DndAlLogSheet("1","1",GameType().DND,"12345678910",
            "Ben Dover","Human","fighter;rogue;rogue","none","none","none",
            "1:1;2:1")
    )
    var dndEntryItemIndex = -1
    var dndLogSheetEntry = DndAlEntry("","","",
        "","","",
        "","","","",
        "","","",
        "","","",
        "","","",
        "","","")

    // use RealmResults to build this list
    var dndLogSheetEntries: List<DndAlEntry> = getDndEntries("1")


    fun onDndEntryItemClicked(index: Int){
        dndEntryItemIndex = index
        dndLogSheetEntry = dndLogSheetEntries[index]
        Router.navigateTo(Screen.EditDNDentryScreen)
    }
    fun onNewDndLogSheetButtonPressed(){
        logsheetItemIndex = -1
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }
    fun onNewDndEntryButtonPressed(){
        dndEntryItemIndex = -1
        Router.navigateTo(Screen.EditDNDentryScreen)
    }
    // test data for ui
    private fun getDndEntries(logsheetID: String): MutableList<DndAlEntry> {
        var dndEntryList = mutableListOf<DndAlEntry>()
        for (i in 0 until (logsheetID.toInt()+3)){
            dndEntryList.add(DndAlEntry((i+1).toString(),myProfile.id,logsheetID,"DDAL",
                "Test Adventure "+(i+1).toString(),(i*3+1).toString()+" January 2021","testDM",
                "1",(i*1000).toString(),(i*2).toString(),(i*3).toString(),
                "Y",(i*1000).toString(),(i).toString(),(i+1).toString(),i.toString(),
                (i*1000).toString(),((i+1)*2).toString(),((i+2)*2).toString(),
                "This "+(i+1)+"was a blast!",i.toString(),i.toString()))
        }
        return dndEntryList
    }
}