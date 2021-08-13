package com.example.tabletopgames.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.tabletopgames.R
import com.example.tabletopgames.repository.TabletopGamesDataRepository
import com.example.tabletopgames.models.*
import com.example.tabletopgames.views.Router
import com.example.tabletopgames.views.Screen
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


open class MainViewModel(private val repo: TabletopGamesDataRepository) : ViewModel() {


    // helper
    private val em = ErrorMessage()
    private val err = em.err
    private val noblanks = em.noblanks
    private val errlogin = em.errlogin
    private val errprofile = em.errprofile
    private val errres = em.errres
    private val comesoon = em.comesoon
    val none = em.none

    fun getImg(gameType: String): Int {
        return when (gameType) {
            GameType().DND -> R.drawable.dungeonsndragons
            GameType().MTG -> R.drawable.magicthegathering
            GameType().MONOP -> R.drawable.monopoly
            else -> R.drawable.multiverselogo
        }
    }

    private fun getMonthName(month: String): String {
        return when (month) {
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
            else -> "January"
        }
    }

    private fun dateCreated(): String {
        val cal: Calendar = Calendar.getInstance()
        val today: Int = cal.get(Calendar.DAY_OF_MONTH)
        val thisMonth: Int = cal.get(Calendar.MONTH) + 1
        val thisYear: Int = cal.get(Calendar.YEAR)
        return today.toString() + " " + getMonthName(thisMonth.toString()) + " " + thisYear.toString()
    }

    private var currentDate = LocalDateTime.now()
    private var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    private var todayF = currentDate.format(formatter)
    private fun getToday(): String { return todayF }
    var errorMessage = none

    //generic navigation functions
    fun backButton() {
        Router.goBack()
    }

    fun onProfilePressed() {
       if (loggedIn){  Router.navigateTo(Screen.MyProfileScreen) }
        else { Router.navigateTo(Screen.EditProfileScreen) }
    }

    fun onReservationsPressed() {
        if (loggedIn){ Router.navigateTo(Screen.ReservationsScreen) }
        else { Router.navigateTo(Screen.EditProfileScreen) }
    }

    fun onLogSheetsPressed() {
        if(loggedIn){ Router.navigateTo(Screen.LogSheetsScreen) }
        else { Router.navigateTo(Screen.EditProfileScreen) }
    }

    fun onHomeButtonPressed() {
        if(loggedIn){ Router.navigateTo(Screen.HomeScreen) }
        else { Router.navigateTo(Screen.EditProfileScreen) }
    }

    //admin stuff
    var gtype = ""
    private val _gameTypeSelected = MutableLiveData("")
    val gameTypeSelected: LiveData<String> = _gameTypeSelected
    val gameType = when(gameTypeSelected.value){
        GameType().DND -> gtype = GameType().DND
        GameType().MTG -> gtype = GameType().MTG
        GameType().MONOP -> gtype = GameType().MONOP
        else -> gtype = GameType().etal
    }
    private val _dayMonthYearSelected = MutableLiveData("")
    val dayMonthYearSelected: LiveData<String> = _dayMonthYearSelected
    private val _timeSelected = MutableLiveData("")
    val timeSelected: LiveData<String> = _timeSelected
    private val _seatSelected = MutableLiveData("")
    val seatSelected: LiveData<String> = _seatSelected
    private val _durationSelected = MutableLiveData("")
    val durationSelected: LiveData<String> = _durationSelected

    //login stuff
    val loginBlank = MyLogin("", "")
    lateinit var myLogin: MyLogin
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    var loggedIn = false
    var newLogin = loginBlank
    var isNewLogin = true
    var emailExists = false

    private fun getUserData(){
        errorMessage = none
        if (loggedIn){
            // ensure lists are clear
            reservationsListOf.clear()
            logSheetList.clear()
            // make calls to get this users records from the ROOM.
            buildReservationList()
            buildLogSheetList()
        }
    }

    private fun emailExists() = viewModelScope.launch{
        errorMessage = none
        // look for email.value in MyLogin ROOM
        // foundEmail = ROOM.MyLogin where email == email.value
        try {
            val result = repo.emailExists(email.value.toString())
            if (result != null) {
                if (result.contains(myLogin)){ emailExists = true }
            }
        } catch (ex: Exception){ errorMessage = "$err viewModel151" }
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onLogoutPressed(){
        errorMessage = none
        loggedIn = false
        newProfile = true
        myLogin = loginBlank
        myProfile = profileBlank
        logSheetList.clear()
        dndLogSheetEntries.clear()
        mtgLogSheetEntries.clear()
        reservationsListOf.clear()
        Router.navigateTo(Screen.LoginScreen)
    }

    fun onLoginPressed() = viewModelScope.launch {
        // validate input
        val isValid = isValidLogin(email.value.toString(),password.value.toString())
        if (isValid) {
            newLogin.email = email.value.toString()
            newLogin.password = password.value.toString()
        }
        // reset globals
        errorMessage = none
        loggedIn = false
        myLogin = loginBlank
        myProfile = profileBlank
        reservationsListOf.clear()
        logSheetList.clear()
        dndLogSheetEntries.clear()
        mtgLogSheetEntries.clear()
        // get the login and profile from ROOM
        try {
            myLogin = repo.findMyLogin(newLogin)!!
            myProfile = repo.getMyProfile(myLogin.email)!!
        } catch (ex: Exception){
            errorMessage = err
        }
        // ensure this login and profile exists
        // and their emails match
        if(!myLogin.equals(null)){
            if(myLogin == newLogin){
                testProfile()
            } else { onLoginFailed() }
        } else { onLoginFailed() }
    }

    private fun testProfile(){
        loggedIn = true
        newProfile = false
        if (myProfile.email != myLogin.email){
            loggedIn = false
            newProfile = true
            myProfile = profileBlank
            Router.navigateTo(Screen.EditProfileScreen)
        } else {
            isNewReservation = true
            getUserData()
            Router.navigateTo(Screen.HomeScreen) }
    }

    private fun onLoginFailed() {
        Log.e(TAG,errlogin)
        loggedIn = false
        newProfile = true
        isNewLogin = true
        onCreateProfilePressed()
    }

    private fun addNewLogin(login: MyLogin) = viewModelScope.launch{
        errorMessage = none
        // add this object to the Room
        try{ repo.addNewLogin(login) }
        catch (ex: Exception){ errorMessage = "$err viewModel238"
        }
    }

    fun isValidLogin(email: String, password: String): Boolean = when {
        email.isEmpty() -> false
        password.isEmpty() -> false
        else -> true
    }

    //profile stuff
    val profileBlank = MyProfile(0, "", "", "", "",User.PUBLIC)
    var newProfile = true
    var myProfile = profileBlank
    private val _firstName = MutableLiveData("")
    val firstName: LiveData<String> = _firstName
    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> = _lastName
    private val _phone = MutableLiveData("")
    val phone: LiveData<String> = _phone

    fun onPhoneChange(newPhone: String) {
        _phone.value = newPhone
    }

    fun onFirstNameChange(newEmail: String) {
        _firstName.value = newEmail
    }

    fun onLastNameChange(newPassword: String) {
        _lastName.value = newPassword
    }

    fun onDeleteProfilePressed()= viewModelScope.launch {
        errorMessage = none
       try {
           repo.deleteThisProfile(myProfile)
           repo.deleteThisLogin(myLogin)
       } catch (ex: Exception){ errorMessage = "$err viewModel286" }
        myLogin=loginBlank
        myProfile=profileBlank
        loggedIn=false
        newProfile=true
        Router.navigateTo(Screen.LoginScreen)
    }

    fun onEditProfilePressed() {
        errorMessage = none
        newProfile = false
        Router.navigateTo(Screen.EditProfileScreen)
    }

    fun onCreateProfilePressed() {
        errorMessage = none
        newProfile = true
        myLogin = MyLogin( "", "Password")
        myProfile = MyProfile(0, "First Name","Last Name","Email","Phone",User.PUBLIC)
        newProfile = true
        Router.navigateTo(Screen.EditProfileScreen)
    }

    fun onSubmitProfilePressed() = viewModelScope.launch{
        errorMessage = none
        if (newProfile){
            if (!profileIsEmpty()){
                emailExists()
                // create myProfile object and myLogin object
                if(emailExists){
                    errorMessage = "$errprofile viewModel 294"
                    Router.navigateTo(Screen.EditProfileScreen)
                } else {
                    errorMessage = none
                    createProfileLoginObjects()
                    loggedIn = true
                    newProfile = false
                    Router.navigateTo(Screen.MyProfileScreen)
                }
            } else {
                errorMessage = noblanks
                Router.navigateTo(Screen.EditProfileScreen)
            }
        } else {
            try {
                updateMyProfile()
                repo.updateThisProfile(myProfile)
                repo.updateThisLogin(myLogin)
                Router.navigateTo(Screen.MyProfileScreen)
            } catch (ex: Exception){ errorMessage = "$err viewModel 313"
            }
        }
    }

    private fun updateMyProfile() {
        if (firstName.value!="")myProfile.firstName=firstName.value.toString()
        if (lastName.value!="")myProfile.lastName=lastName.value.toString()
        if (phone.value!="")myProfile.phone=phone.value.toString()
        if (email.value!=""){
            myProfile.email=email.value.toString()
            myLogin.email=email.value.toString()
        }
        if (password.value!=""){
            myLogin.password=password.value.toString()
        }
    }

    private fun createProfileLoginObjects() = viewModelScope.launch{
        myLogin.email = email.value.toString()
        myProfile.email = email.value.toString()
        myLogin.password = password.value.toString()
        myProfile.firstName = firstName.value.toString()
        myProfile.lastName = lastName.value.toString()
        myProfile.phone = phone.value.toString()
        myProfile.userType = User.PUBLIC
        // add the new login and profile objects to the ROOM
        addNewLogin(myLogin)
        addNewProfile(myProfile)
    }

    private fun profileIsEmpty(): Boolean {
        var isEmpty = true
        if (email.value.toString() != "" && password.value.toString() != ""
            && firstName.value.toString() != "" && lastName.value.toString() != ""
            && phone.value.toString() != "") {
            isEmpty = false
        }
        return isEmpty
    }

    private suspend fun addNewProfile(profile: MyProfile)= viewModelScope.launch {
        errorMessage = none
        // add myProfile to the ROOM
        try {
            repo.addNewProfile(profile)
        } catch (ex: Exception){
            errorMessage = "$err viewModel360"
        }
    }


    /*
    *               This is where ReservationsViewModel
    *               code begins.
    *
    *
    * */



    //reservation stuff
    /*
    *       The seatingChart variable contains information on
    *       each of the seats at the game tables.
    *       each seat has a timeBlock that begins with
    *       12 hours. no reservation may be added that has
    *       a duration that exceeds a give seat's timeBlock.
    *       each new reservation must compare their start time
    *       and end tim to each existing reservation of that day, and
    *       may not begin nor end during an existing reservation's
    *       timeBlock(i.e. startTime,endTime,duration)
    * */
    var reservationsItemIndex = -1
    var isNewReservation = true
    private val _gametype = MutableLiveData("")
    val gametype: LiveData<String> = _gametype
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
    val duration: LiveData<String> = _duration

    var reservationBlank = Reservation(
        0, 0, "",
        "", "", "", "", ""
    )
    var dummyReservation = Reservation(0,0,GameType().DND,dateCreated(),
            "12:00 AM","1","2","4")
    var reservation = reservationBlank
    val reservationListEmpty = mutableListOf<Reservation>()
    var reservationsListOf = reservationListEmpty
    private var reservationExists = false

    fun onGameTypeChange(gameType: String) {
        _gametype.value = gameType
    }

    fun onDayChange(day: String) {
        _day.value = day
    }

    fun onMonthChange(month: String) {
        _month.value = month
    }

    fun onTimeChange(time: String) {
        _time.value = time
    }

    fun onTableChange(table: String) {
        _table.value = table
    }

    fun onSeatChange(seat: String) {
        _seat.value = seat
    }

    fun onDurationChange(duration: String) {
        _duration.value = duration
    }

    private fun getNewResYear(day: String, month: String): Int {
        val cal: Calendar = Calendar.getInstance()
        val today: Int = cal.get(Calendar.DAY_OF_MONTH)
        val thisMonth: Int = cal.get(Calendar.MONTH) + 1
        val thisYear: Int = cal.get(Calendar.YEAR)
        // the reservation is for this year unless
        // the day and month are before today
        // or the day is today but the month is before this month
        var resYear: Int = thisYear

        if (today>day.toInt()&&thisMonth>month.toInt()){ resYear++ }
        else{
            if (today==day.toInt()&&thisMonth>month.toInt()){ resYear++ }
        }
        return resYear
    }

    fun onSubmitReservationButtonPressed() = viewModelScope.launch {
        errorMessage = none
        val dayV = day.value.toString()
        val monthV = month.value.toString()
        val year = getNewResYear(dayV, monthV)
        val dayMonthYear = dayV + " " + getMonthName(monthV) + " " + year.toString()

        if (isNewReservation) {
            if(isValidReservation()){
                reservation.gameType = gametype.value.toString()
                reservation.dayMonthYear = dayMonthYear
                reservation.time = time.value.toString()
                reservation.gameTable = table.value.toString()
                reservation.seat = seat.value.toString()
                reservation.duration = duration.value.toString()
                reservation.profileID = myProfile.id
                //if no reservation with this date, time, and location exists
                //then add to Reservation Realm and add to reservationsListOf
                var reservationSearchResults: List<Reservation>? = null
                    try {
                        reservationSearchResults = repo.thisDaysReservationsFor(reservation.dayMonthYear,reservation.gameTable,reservation.seat)!!
                    } catch (ex: Exception){ errorMessage = "$err viewmodel 479" }
                // compare newReservation to seatingChart to see if it fits
                // if not send suggestions message
                reservationSearchResults?.forEach{ existingReservation ->
                    if (existingReservation.dayMonthYear==reservation.dayMonthYear &&
                        existingReservation.gameTable==reservation.gameTable &&
                        existingReservation.seat==reservation.seat &&
                        existingReservation.time==reservation.time){
                        reservationExists=true
                    }
                }
                if (reservationExists) {
                    errorMessage = errres
                    Router.navigateTo(Screen.NewReservationScreen)
                } else {
                   try {
                       repo.addNewReservation(reservation)
                       reservationsListOf.add(reservation)
                   } catch (ex: Exception){ errorMessage = "$err viewmodel 502"}
                    // send details message to Admin of success
                    // update reservation seating chart

                    Router.navigateTo(Screen.ReservationsScreen)
                }
            } else {
                errorMessage = noblanks
                Router.navigateTo(Screen.NewReservationScreen)
            }
        } else {
            if (gametype.value != "") {
                reservationsListOf[reservationsItemIndex].gameType = gametype.value.toString()
            }
            if (dayMonthYear != "") {
                reservationsListOf[reservationsItemIndex].dayMonthYear = dayMonthYear
            }
            if (table.value != "") {
                reservationsListOf[reservationsItemIndex].gameTable = table.value.toString()
            }
            if (seat.value != "") {
                reservationsListOf[reservationsItemIndex].seat = seat.value.toString()
            }
            if (duration.value != "") {
                reservationsListOf[reservationsItemIndex].duration = duration.value.toString()
            }
            if (time.value != "") {
                reservationsListOf[reservationsItemIndex].time = time.value.toString()
            }
            errorMessage = try {
                repo.updateThisReservation(reservationsListOf[reservationsItemIndex])
                none
            } catch (ex: Exception){ err }
            if (errorMessage == err){
                Router.navigateTo(Screen.NewReservationScreen)
            }
            Router.navigateTo(Screen.ReservationsScreen)
        }
    }

    private fun isValidReservation(): Boolean {
        var isValidReservation = true
        if (gametype.value == "") {
            isValidReservation = false
        }
        if (table.value == "") {
            isValidReservation = false
        }
        if (seat.value == "") {
            isValidReservation = false
        }
        if (duration.value == "") {
            isValidReservation = false
        }
        if (time.value == "") {
            isValidReservation = false
        }
        errorMessage = if (!isValidReservation){ noblanks }
                        else { none }
        return isValidReservation
    }

    fun onEditReservationButtonPressed() {
        errorMessage = none
        isNewReservation = false
        Router.navigateTo(Screen.NewReservationScreen)
    }

    fun onNewReservationButtonPressed() {
        errorMessage = none
        reservationsItemIndex = -1
        isNewReservation = true
        Router.navigateTo(Screen.NewReservationScreen)
    }

    fun onDeleteReservationButtonPressed() = viewModelScope.launch {
        errorMessage = none
        //delete reservationsListOf[itemIndex]
        //remove It from Room
        try {
            repo.deleteThisReservation(reservationsListOf[reservationsItemIndex])
            reservationsListOf.removeAt(reservationsItemIndex)
            isNewReservation = true
        } catch (ex: Exception){ errorMessage = "$err viewModel598"
        }
        Router.navigateTo(Screen.ReservationsScreen)
    }

    fun onReservationListItemClicked(index: Int) {
        errorMessage = none
        reservationsItemIndex = index
        reservation = reservationsListOf[index]
        Router.navigateTo(Screen.ReservationDetailsScreen)
    }

    private fun buildReservationList()= viewModelScope.launch {
        try {
            reservationsListOf = repo.getReservationsFor(myProfile.id) as MutableList<Reservation>
        } catch (ex: java.lang.Exception){
            errorMessage = "$err viewModel608"
        }
    }







    /*
    *
    *               This is where LogSheetsViewModel
    *               code begins.
    *
    *
    *
    * */






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
    var logSheetList = mutableListOf<LogSheet>()
    var dummyLogSheet = LogSheet(0,0,GameType().DND,dateCreated())
    var logSheetItemBlank = LogSheet(0, 0, "", "")
    var logSheetItem = logSheetItemBlank
    var logsheetItemIndex = -1

    private fun buildLogSheetList() = viewModelScope.launch {
        try {
            logSheetList = repo.getLogSheetsFor(myProfile.id)?.toMutableList()!!
        } catch (ex: Exception){
            errorMessage = "$err viewModel656"
        }
    }

    fun onLogSheetListItemClicked(index: Int) = viewModelScope.launch{
        logSheetItem = logSheetList[index]
        var playerObject = playersBlank
        logsheetItemIndex = index
        when (logSheetItem.gameType) {
            GameType().DND -> {
                isNewDndLogSheet = false
                try {
                    dndLogSheet = repo.getThisDndLogSheet(logSheetItem.profileID,logSheetItem.id)!!
                    dndLogSheetEntries = repo.getAllDndEntriesForThisPC(logSheetItem.profileID,logSheetItem.id) as MutableList<DndAlEntry>
                } catch (ex: Exception){ errorMessage = "$err viewmodel 655" }
                Router.navigateTo(Screen.DndLogSheetScreen)
            }
            GameType().MTG -> {
                isNewMtgLogSheet = false
                try {
                    mtgLogSheet = repo.getThisMtgLogSheet(logSheetItem.profileID,logSheetItem.id)!!
                    mtgLogSheetEntries = repo.getAllMtgEntriesFor(logSheetItem.profileID,logSheetItem.id) as MutableList<MtgEntry>
                    playerObject = repo.getPlayerObjectForThisGame(logSheetItem.profileID,logSheetItem.id)!!
                } catch (ex: Exception){ errorMessage = "$err viewmodel 662" }
                playersList = playerObject.members.split(";") as MutableList<String>
                Router.navigateTo(Screen.MtgLogSheetScreen)
            }
            else -> Router.navigateTo(Screen.HomeScreen)
        }
    }

    //dnd log sheet text field values
    private val _playerdcinumber = MutableLiveData("")
    val playerdcinumber: LiveData<String> = _playerdcinumber
    fun onPlayerDCInumberChanged(playerDCInumber: String) {
        _playerdcinumber.value = playerDCInumber
    }

    private val _charactername = MutableLiveData("")
    val charactername: LiveData<String> = _charactername
    fun onCharacterNameChange(name: String) {
        _charactername.value = name
    }

    private val _characterrace = MutableLiveData("")
    val characterrace: LiveData<String> = _characterrace
    fun onCharacterRaceChange(race: String) {
        _characterrace.value = race
    }

    private val _classes = MutableLiveData("")
    val classes: LiveData<String> = _classes
    fun onClassChange(classes: String) {
        _classes.value = classes
    }
    //dndAlLogSheet.classes is calculated from the entries; the default is 'Fighter'

    private val _faction = MutableLiveData("")
    val faction: LiveData<String> = _faction
    fun onFactionChange(faction: String) {
        _faction.value = faction
    }
    var isNewDndLogSheet = false

    fun onNewDndLogSheetButtonPressed() {
        errorMessage = none
        isNewDndLogSheet = true
        dndLogSheetEntries.clear()
        dndEntryItemIndex = -1
        dndLogSheetItemIndex = -1
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }

    fun onNewMtgLogSheetButtonPressed() {
        errorMessage = none
        isNewMtgLogSheet = true
        mtgLogSheetEntries.clear()
        mtgEntryItemIndex = -1
        logsheetItemIndex = -1
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }

    var dndLogSheetBlank = DndAlLogSheet(0,
        0,GameType().DND, "",
        "", "", "", "")
    var dndLogSheet = dndLogSheetBlank
    var dndLogSheetItemIndex = -1
    var dndEntryItemIndex = -1
    var dndLogSheetEntryBlank = DndAlEntry(
        0, 0, 0,
        "", "", "",
        "", "", "", "",
        "", "", "",
        "", "", "",
        "", "", "",
        ""
    )
    var dndLogSheetEntriesEmpty = mutableListOf<DndAlEntry>()
    var dndLogSheetEntry = dndLogSheetEntryBlank
    private val _advname = MutableLiveData("")
    val advname: LiveData<String> = _advname
    fun onAdvNameChange(name: String) {
        _advname.value = name
    }

    private val _advcode = MutableLiveData("")
    val advcode: LiveData<String> = _advcode
    fun onAdvCodeChange(code: String) {
        _advcode.value = code
    }

    private val _dayMonthYear = MutableLiveData("")
    val dayMonthYear: LiveData<String> = _dayMonthYear
    fun onDayMonthYearChange(date: String) {
        _dayMonthYear.value = date
    }

    private val _dmdcinumber = MutableLiveData("")
    val dmdcinumber: LiveData<String> = _dmdcinumber
    fun onDmDciNumberChange(number: String) {
        _dmdcinumber.value = number
    }

    private val _goldplusminus = MutableLiveData("")
    val goldplusminus: LiveData<String> = _goldplusminus
    fun onGoldPlusMinusChange(gold: String) {
        _goldplusminus.value = gold
    }

    private val _downtimeplusminus = MutableLiveData("")
    val downtimeplusminus: LiveData<String> = _downtimeplusminus
    fun onDowntimePlusMinusChange(downtime: String) {
        _downtimeplusminus.value = downtime
    }

    private val _permanentmagicitemsplusminus = MutableLiveData("")
    val permanentmagicitemsplusminus: LiveData<String> = _permanentmagicitemsplusminus
    fun onPermanentMagicItemsPlusMinusChange(magicitems: String) {
        _permanentmagicitemsplusminus.value = magicitems
    }

    private val _newclass = MutableLiveData("")
    val newclass: LiveData<String> = _newclass
    fun onNewClassChange(newclass: String) {
        _newclass.value = newclass
    }

    private val _advnotes = MutableLiveData("")
    val advnotes: LiveData<String> = _advnotes
    fun onAdvNotesChange(notes: String) {
        _advnotes.value = notes
    }

    private fun getStartingLevel(): String {
        var startlvl = "1"
        var count = 0
        var listOfClasses: List<String>? = null
        if (!dndLogSheetEntries.isNullOrEmpty()){
            listOfClasses = dndLogSheet.classes.split(";")
            count = listOfClasses.size
        }
        startlvl = count.toString()
        return startlvl
    }

    private fun getStartingGold(): String {
        var startgold = ""
        var startinggold = 0
        if (!dndLogSheetEntries.isNullOrEmpty()){
            dndLogSheetEntries.forEach{ entry ->
                startinggold += entry.goldPlusMinus.toInt()
            }
        }
        startgold = startinggold.toString()
        return startgold
    }

    private fun getStartingDowntime(): String {
        var startdowntime = ""
        var startingdowntime = 0
        if (!dndLogSheetEntries.isNullOrEmpty()){
            dndLogSheetEntries.forEach{ entry ->
                startingdowntime += entry.downtimePlusMinus.toInt()
            }
        }
        startdowntime = startingdowntime.toString()
        return startdowntime
    }

    fun getStartingMagicItems(): String {
        var startmagicitems = ""
        var startingmagicitems = 0
        if (!dndLogSheetEntries.isNullOrEmpty()){
            dndLogSheetEntries.forEach{ entry ->
                startingmagicitems += entry.permanentMagicItemsPlusMinus.toInt()
            }
        }
        startmagicitems = startingmagicitems.toString()
        return startmagicitems
    }

    fun onEditDndLogSheetButtonPressed() {
        errorMessage = none
        isNewDndLogSheet = false
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }

    fun onDeleteDndLogSheetButtonPressed()= viewModelScope.launch {
        errorMessage = none
        isNewDndLogSheet = true
        repo.deleteThisDndLogSheet(dndLogSheet)
        repo.deleteThisLogSheet(logSheetList[logsheetItemIndex])
        repo.deleteAllDndEntriesFor(myProfile.id,dndLogSheet.id)
        logSheetList.removeAt(logsheetItemIndex)
        dndLogSheetEntries.clear()
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    fun onSubmitDndLogSheetButtonPressed() = viewModelScope.launch{
        errorMessage = none
        if (isNewDndLogSheet) { //new logsheet add to dndLogSheets and logsheets
            // create new LogSheet
            var newLogSheet = LogSheet(0,myProfile.id,GameType().DND,dateCreated())
            // add it to Room
           try {
               newLogSheet.id = repo.addNewLogSheet(newLogSheet).toInt()
           } catch (ex: Exception){ errorMessage = "$err viewModel 1011" }
            // use it.id to create child logsheets
            var newDndLogSheet = DndAlLogSheet(
                newLogSheet.id,newLogSheet.profileID,
                newLogSheet.gameType,playerdcinumber.value.toString(),charactername.value.toString(),
                characterrace.value.toString(),classes.value.toString(),faction.value.toString()
            )
            try {
                repo.addNewDndLogSheet(newDndLogSheet)
            } catch (ex: Exception){ errorMessage = "$err viewmodel 1020"}
            logSheetList.add(newLogSheet)
            errorMessage = none
        } else { // old logsheet edit at logsheetItemIndex
            if (!playerdcinumber.equals("")) {
                dndLogSheet.playerDCInumber = playerdcinumber.value.toString()
            }
            if (!charactername.equals("")) {
                dndLogSheet.characterName = charactername.value.toString()
            }
            if (!characterrace.equals("")) {
                dndLogSheet.characterRace = characterrace.value.toString()
            }
            if (!faction.equals("")) {
                dndLogSheet.faction = faction.value.toString()
            }
            try {
                repo.updateThisDndLogSheet(dndLogSheet)
            } catch (ex: Exception){ errorMessage = "$err viewmodel 905" }
            errorMessage = none
        }
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    // user Room to build this list
    var dndLogSheetEntries = mutableListOf<DndAlEntry>()
    var isNewDndEntry = false

    fun onDndEntryItemClicked(index: Int) {
        errorMessage = none
        dndEntryItemIndex = index
        dndLogSheetEntry = dndLogSheetEntries[index]
        Router.navigateTo(Screen.DndEntryScreen)
    }

    private fun getTotalGold(startgold: String, plusminus: String): String {
        val startingGold = startgold.toInt()
        val plusminus = plusminus.toInt()
        val goldtotal = startingGold + plusminus
        return goldtotal.toString()
    }

    private fun getTotalDowntime(startdowntime: String, plusminus: String): String {
        val startingDowntime = startdowntime.toInt()
        val plusminus = plusminus.toInt()
        val downtimetotal = startingDowntime + plusminus
        return downtimetotal.toString()
    }

    private fun getTotalPermanentMagicItems(startmagicitems: String, plusminus: String): String {
        val startmagicitems = startmagicitems.toInt()
        val plusminus = plusminus.toInt()
        val magicitemstotal = startmagicitems + plusminus
        return magicitemstotal.toString()
    }

    fun onNewDndEntryButtonPressed() {
        errorMessage = none
        isNewDndEntry = true
        dndEntryItemIndex = -1
        Router.navigateTo(Screen.EditDNDentryScreen)
    }

    fun onEditDndEntryButtonPressed() {
        errorMessage = none
        isNewDndEntry = false
        Router.navigateTo(Screen.EditDNDentryScreen)
    }

    fun onDeleteDndEntryButtonPressed() = viewModelScope.launch{
        errorMessage = none
        //only allow delete of most recent entry
        dndLogSheetEntries.remove(dndLogSheetEntry)
        try {
            repo.deleteThisDndEntry(dndLogSheetEntry)
        } catch (ex: Exception){ errorMessage = "$err viewmodel 956" }
        Router.navigateTo(Screen.DndLogSheetScreen)
    }

    fun onSubmitDndEntryButtonPressed(){
        errorMessage = none
        var newEntry = dndLogSheetEntryBlank
        //get data from viewModel variables
        //change object in list
        //change Room
        if (isNewDndEntry) {// make a new entry and add it to the list
            newEntry.logsheetID = dndLogSheet.id
            newEntry.adventureName = advname.value.toString()
            newEntry.adventureCode = advcode.value.toString()
            newEntry.profileID = myProfile.id
            newEntry.dayMonthYear = getToday()
            newEntry.dmDCInumber = dmdcinumber.value.toString()
            if (isNewDndLogSheet) {
                newEntry.startingLevel = classes.value.toString()
                newEntry.startingGold = (0).toString()
                newEntry.startingDowntime = (0).toString()
                newEntry.startingPermanentMagicItems = (0).toString()
                isNewDndLogSheet = false
            } else {
                newEntry.startingLevel = getStartingLevel()
                newEntry.startingGold = getStartingGold()
                newEntry.startingDowntime = getStartingDowntime()
                newEntry.startingPermanentMagicItems = getStartingMagicItems()
            }
            newEntry.levelAccepted = "Y"
            newEntry.goldPlusMinus = goldplusminus.value.toString()
            newEntry.downtimePlusMinus = downtimeplusminus.value.toString()
            newEntry.permanentMagicItemsPlusMinus = permanentmagicitemsplusminus.value.toString()
            newEntry.newClassLevel = newclass.value.toString()
            newEntry.newGoldTotal = getTotalGold(newEntry.startingGold, newEntry.goldPlusMinus)
            newEntry.newDowntimeTotal = getTotalDowntime(newEntry.startingDowntime, newEntry.downtimePlusMinus)
            newEntry.newPermanentMagicItemTotal = getTotalPermanentMagicItems(
                newEntry.startingPermanentMagicItems,
                newEntry.permanentMagicItemsPlusMinus
            )
            newEntry.adventureNotes = advnotes.value.toString()
            addNewDndEntry(newEntry)
        } else { // edit dndEntries[dndEntryItemIndex]
            if (advname.value.toString() != "") {
                dndLogSheetEntries[dndEntryItemIndex].adventureName = advname.value.toString()
            }
            if (advname.value.toString() != "") {
                dndLogSheetEntries[dndEntryItemIndex].adventureCode = advcode.value.toString()
            }
            if (dmdcinumber.value.toString() != "") {
                dndLogSheetEntries[dndEntryItemIndex].dmDCInumber = dmdcinumber.value.toString()
            }
            if (goldplusminus.value.toString() != "") {
                dndLogSheetEntries[dndEntryItemIndex].goldPlusMinus = goldplusminus.value.toString()
                dndLogSheetEntries[dndEntryItemIndex].newGoldTotal =
                    (dndLogSheetEntries[dndEntryItemIndex].startingGold.toInt() + newEntry.goldPlusMinus.toInt()).toString()
            }
            if (downtimeplusminus.value.toString() != "") {
                dndLogSheetEntries[dndEntryItemIndex].downtimePlusMinus =
                    downtimeplusminus.value.toString()
                dndLogSheetEntries[dndEntryItemIndex].newDowntimeTotal =
                    (dndLogSheetEntries[dndEntryItemIndex].startingDowntime.toInt() + newEntry.downtimePlusMinus.toInt()).toString()
            }
            if (permanentmagicitemsplusminus.value.toString() != "") {
                dndLogSheetEntries[dndEntryItemIndex].permanentMagicItemsPlusMinus =
                    permanentmagicitemsplusminus.value.toString()
                dndLogSheetEntries[dndEntryItemIndex].newPermanentMagicItemTotal =
                    (dndLogSheetEntries[dndEntryItemIndex].startingPermanentMagicItems.toInt() + newEntry.permanentMagicItemsPlusMinus.toInt()).toString()
            }
            if (newclass.value.toString() != "") {
                dndLogSheetEntries[dndEntryItemIndex].newClassLevel = newclass.value.toString()
            }
            updateThisDndEntry(dndLogSheetEntries[dndEntryItemIndex])
        }
        Router.navigateTo(Screen.DndLogSheetScreen)
    }

    private fun addNewDndEntry(entry: DndAlEntry)=viewModelScope.launch{
       try {
           repo.addNewDndEntry(entry)
       }catch (ex: Exception){errorMessage = "$err viewmodel 1136"}
        dndLogSheetEntries.add(entry)
    }

    private fun updateThisDndEntry(entry: DndAlEntry)=viewModelScope.launch{
        try {
            repo.updateThisDndEntry(entry)
        }catch (ex: Exception){errorMessage = "$err viewmodel 1143"}
    }

    //mtg log sheet stuff
    var isNewMtgLogSheet = false
    var mtgLogSheetBlank = MTGlogsheet(
        0, 0, GameType().MTG,
        0, ""
    )
    var mtgLogSheets = mutableListOf<MTGlogsheet>()
    var mtgLogSheetEntries = mutableListOf<MtgEntry>()
    var mtgLogSheetEntryBlank = MtgEntry(0,0, 0, "","")
    var mtgLogSheetEntry = mtgLogSheetEntryBlank
    var mtgLogSheetEntriesEmpty = mutableListOf<MtgEntry>()
    var mtgLogSheet = mtgLogSheetBlank
    var mtgLogSheetIndex = -1
    var mtgEntryItemIndex = -1
    var magicGameNumber = 1
    var playersListEmpty = mutableListOf<String>()
    var newPlayersList = playersListEmpty
    var playersList = playersListEmpty
    var isAddingNewPlayers = false
    var playersBlank = Players(0,0,0,"")
    var myPlayers = playersBlank

    private val _newPlayersString = MutableLiveData("")
    val newPlayersString: LiveData<String> = _newPlayersString
    fun onPlayersChange(newplayers: String) {
        _newPlayersString.value = newplayers
    }

    private var _winner = MutableLiveData("")
    var winner: LiveData<String> = _winner
    fun onWinnerChange(winner: String) {
        _winner.value = winner
    }

    fun onNewMtgLogSheetEntryButtonPressed() {
        errorMessage = none
        mtgEntryItemIndex = -1
        isNewMtgEntry = true
        Router.navigateTo(Screen.EditMTGentryScreen)
    }

    fun onEditMtgLogSheetButtonPressed() {
        errorMessage = none
        isNewMtgLogSheet = false
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }

    fun onDeleteMtgLogSheetButtonPressed()= viewModelScope.launch{
        errorMessage = none
        try {
            repo.deleteThisMtgLogSheet(mtgLogSheet)
            repo.deleteThisLogSheet(logSheetList[logsheetItemIndex])
        } catch (ex: Exception){ errorMessage = "$err viewmodel 1095" }
        logSheetList.removeAt(logsheetItemIndex)
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    fun onAddPlayerButtonPressed(players: String) {
        isAddingNewPlayers = true
        newPlayersList = players.split("\r").toMutableList()
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }

    fun onSubmitMtgLogSheetButtonPressed() = viewModelScope.launch{
        errorMessage = none
        var newMtgLogSheet = mtgLogSheetBlank
        var newPlayers = playersBlank
        var newLogSheet = dummyLogSheet
        val separator = ";"
        // create a LogSheet object
        if (isNewMtgLogSheet){
            newLogSheet.gameType = GameType().MTG
            newLogSheet.profileID = myProfile.id
            newLogSheet.dateCreated = dateCreated()
            try {
                newLogSheet.id = repo.addNewLogSheet(newLogSheet).toInt()
            } catch (ex: Exception){ errorMessage = "$err viewmodel 1119" }
            newPlayers.logsheetID = newLogSheet.id
            newPlayers.profileID = newLogSheet.profileID
            newPlayers.members = newPlayersList.joinToString(separator)
            try {
                newPlayers.id = repo.addNewPlayersForANewGame(newPlayers).toInt()
            } catch (ex: Exception){ errorMessage = "$err viewmodel 1125" }
            newMtgLogSheet.id = newLogSheet.id
            newMtgLogSheet.playersID = newPlayers.id
            newMtgLogSheet.dayMonthYear = dateCreated()
            newMtgLogSheet.gameType = newLogSheet.gameType
            newMtgLogSheet.profileID = newLogSheet.profileID
            try {
                repo.addNewMtgLogSheet(newMtgLogSheet)
            } catch (ex: Exception){ errorMessage = "$err viewmodel 1127" }
            mtgLogSheet = newMtgLogSheet
            logSheetList.add(newLogSheet)
        } else {
            try {
                newPlayers = repo.getPlayerObjectForThisGame(mtgLogSheet.profileID,mtgLogSheet.id)!!
            } catch (ex: Exception){ errorMessage = "$err viewmodel 1133" }
            newPlayers.members = newPlayersList.joinToString(separator)
            try {
                repo.updatePlayersForThisGame(newPlayers)
                repo.updateThisMtgLogSheet(mtgLogSheet)
            } catch (ex: Exception){ errorMessage = "$err viewmodel 1143" }
        }
        isAddingNewPlayers = false
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    //new mtg entry item
    var isNewMtgEntry = false

    fun onMtgEntryItemClicked(index: Int, count: Int) {
        errorMessage = none
        mtgEntryItemIndex = index
        magicGameNumber = count
        mtgLogSheetEntry = mtgLogSheetEntries[index]
        Router.navigateTo(Screen.MtgEntryScreen)
    }

    fun onEditMtgEntryButtonPressed() {
        errorMessage = none
        isNewMtgEntry = false
        Router.navigateTo(Screen.EditMTGentryScreen)
    }

    fun onDeleteMtgEntryButtonPressed() = viewModelScope.launch{
        errorMessage = none
        //delete from Room
        repo.deleteThisMtgEntry(mtgLogSheetEntry)
        // and local list
        mtgLogSheetEntries.removeAt(mtgEntryItemIndex)
        Router.navigateTo(Screen.MtgLogSheetScreen)
    }

    fun onSubmitMtgEntryButtonPressed()= viewModelScope.launch {
        errorMessage = none
        var newMagicEntry = mtgLogSheetEntryBlank
        if(isNewMtgEntry){
            // create a mtgEntry object
            newMagicEntry.dayMonthYear = dateCreated()
            newMagicEntry.logsheetID = logSheetItem.id
            newMagicEntry.profileID = logSheetItem.profileID
            newMagicEntry.winner = winner.value.toString()
            // add it to Room
            try {
                newMagicEntry.id = repo.addNewMtgEntry(newMagicEntry).toInt()
            } catch (ex: Exception){ errorMessage = "$err viewmodel 1187" }
            // add it to mtgEntriesList
            mtgLogSheetEntries.add(newMagicEntry)
            Router.navigateTo(Screen.MtgLogSheetScreen)
        } else {
            // update the mtgEntry object
            mtgLogSheetEntry.winner = winner.value.toString().replace("\r",";")
            try {
                repo.updateThisMtgEntry(mtgLogSheetEntry)
            } catch (ex: Exception){ errorMessage = "$err viewmodel 1196" }
            // update the mtgEntriesList
            mtgLogSheetEntries[mtgEntryItemIndex].winner = mtgLogSheetEntry.winner
            Router.navigateTo(Screen.MtgEntryScreen)
        }
    }

}

class ViewModelFactory(private val repo: TabletopGamesDataRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}