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

    private fun getNewID(): String {
        return Calendar.getInstance().timeInMillis.toString() +
                " " + UUID.randomUUID().toString()
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
    var errorMessage = R.string.none.toString()



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
    var myLogin = loginBlank
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    var loggedIn = false
    private val loginFailMessage = "Failed to register this user."
    private val invalidEmailPassword = "Invalid email or password."
    var newLogin = loginBlank
    var emailExists = false

    fun getUserData() = viewModelScope.launch {
        if (loggedIn){
            // make calls to get this users records from the ROOM.
            buildReservationList()
            buildLogSheetList()
            buildDndLogSheets()
            buildMtgLogSheets()
        }
    }

    private fun emailExists() = viewModelScope.launch{
        // look for email.value in MyLogin ROOM
        // foundEmail = ROOM.MyLogin where email == email.value
        try {
            val result = repo.emailExists(email.value.toString())
            if (result != null) {
                if (result.contains(myLogin)){ emailExists = true }
            }
        } catch (ex: Exception){ errorMessage = R.string.errorhasoccurred.toString() }
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginPressed() = viewModelScope.launch {
        // validate input
        val isValid = isValidLogin(email.value.toString(),password.value.toString())
        if (isValid) {
            newLogin.email = email.value.toString()
            newLogin.password = password.value.toString()
        }
        // look for newLogin in ROOM.MyLogin where email == newLogin.email
        //                                  && password == newLogin.password
        try{
            myLogin = repo.findMyLogin(newLogin)!!
        } catch (ex: Exception){ errorMessage = R.string.loginnotfound.toString() }
        if(!myLogin.equals(null)){
            if(myLogin == newLogin){
                try{
                    myProfile = repo.getMyProfile(myLogin.email)!!
                } catch (ex: Exception){
                    errorMessage = R.string.loginnotfound.toString()
                }
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
            myProfile = testProfile
        } else {
            isNewReservation = true
            getUserData()
            Router.navigateTo(Screen.HomeScreen) }
    }

    private fun onLoginFailed() {
        Log.e(TAG,R.string.invalidlogin.toString())
        loggedIn = false
        onCreateProfilePressed()
    }

    private fun addNewLogin(login: MyLogin) = viewModelScope.launch{
        // add this object to the Room
        try{ repo.addNewLogin(login) }
        catch (ex: Exception){ errorMessage = R.string.errorhasoccurred.toString() }
    }

    fun isValidLogin(email: String, password: String): Boolean = when {
        email.isEmpty() -> false
        password.isEmpty() -> false
        else -> true
    }

    //profile stuff
    val profileBlank = MyProfile(0, "", "", "", "",User.PUBLIC)
    var newProfile = true
    val testProfile = MyProfile(
        1, "Test", "Profile",
        "test@gmail.com", "8178675309",User.PUBLIC
    )
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
       try {
           repo.deleteThisProfile(myProfile)
           repo.deleteThisLogin(myLogin)
           myLogin=loginBlank
           myProfile=profileBlank
           loggedIn=false
           newProfile=true
           Router.navigateTo(Screen.LoginScreen)
       } catch (ex: Exception){
           errorMessage = R.string.errorhasoccurred.toString()
       }
    }

    fun onEditProfilePressed() {
        newProfile = false
        Router.navigateTo(Screen.EditProfileScreen)
    }

    fun onCreateProfilePressed() {
        myLogin = MyLogin( "", "Password")
        myProfile = MyProfile(
            0, "First Name",
            "Last Name", "Email", "Phone",User.PUBLIC
        )
        newProfile = true
        Router.navigateTo(Screen.EditProfileScreen)
    }

    fun onSubmitProfilePressed() = viewModelScope.launch{
        if (newProfile){
            if (!profileIsEmpty()){
                emailExists()
                // create myProfile object and myLogin object
                if(emailExists){
                    errorMessage = R.string.emailexists.toString()
                    Router.navigateTo(Screen.EditProfileScreen)
                } else {
                    errorMessage = R.string.none.toString()
                    createProfileLoginObjects()
                    loggedIn = true
                    newProfile = false
                    Router.navigateTo(Screen.MyProfileScreen)
                }
            } else {
                errorMessage = R.string.noblankfields.toString()
                Router.navigateTo(Screen.EditProfileScreen)
            }
        } else {
            try {
                updateMyProfile()
                repo.updateThisProfile(myProfile)
                repo.updateThisLogin(myLogin)
                Router.navigateTo(Screen.MyProfileScreen)
            } catch (ex: Exception){ errorMessage = R.string.errorhasoccurred.toString() }
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
        // add myProfile to the ROOM
        try {
            repo.addNewProfile(profile)
        } catch (ex: Exception){
            errorMessage = R.string.errorhasoccurred.toString()
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
    private var newReservation = reservationBlank
    var reservationsListOf = mutableListOf<Reservation>()
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
        val dayV = day.value.toString()
        val monthV = month.value.toString()
        val year = getNewResYear(dayV, monthV)
        val dayMonthYear = dayV + " " + getMonthName(monthV) + " " + year.toString()

        if (isNewReservation) {
            if(isValidReservation()){
                newReservation.gameType = gametype.value.toString()
                newReservation.dayMonthYear = dayMonthYear
                newReservation.time = time.value.toString()
                newReservation.gameTable = table.value.toString()
                newReservation.seat = seat.value.toString()
                newReservation.duration = duration.value.toString()
                newReservation.profileID = myProfile.id
                //if no reservation with this date, time, and location exists
                //then add to Reservation Realm and add to reservationsListOf
                try {
                    val reservationSearchResults: List<Reservation> =
                        repo.thisDaysReservationsFor(newReservation.dayMonthYear,newReservation.gameTable,newReservation.seat)!!
                    // compare newReservation to seatingChart to see if it fits
                    // if not send suggestions message
                    reservationSearchResults.forEach{ existingReservation ->
                        if (existingReservation.dayMonthYear==newReservation.dayMonthYear &&
                            existingReservation.gameTable==newReservation.gameTable &&
                            existingReservation.seat==newReservation.seat &&
                            existingReservation.time==newReservation.time){
                            reservationExists=true
                        }
                    }
                    if (reservationExists) {
                        errorMessage = R.string.reservationexists.toString()
                        Router.navigateTo(Screen.NewReservationScreen)
                    } else {
                        repo.addNewReservation(newReservation)
                        reservationsListOf.add(newReservation)
                        // send details message to Admin of success
                        // update reservation seating chart

                        Router.navigateTo(Screen.ReservationsScreen)
                    }
                } catch (ex: Exception){ errorMessage = R.string.errorhasoccurred.toString() }
            } else {
                errorMessage = R.string.noblankfields.toString()
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
                R.string.none.toString()
            } catch (ex: Exception){ R.string.errorhasoccurred.toString() }
            if (errorMessage==R.string.errorhasoccurred.toString()){
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
        errorMessage = if (!isValidReservation){ R.string.noblankfields.toString() }
                        else { R.string.none.toString() }
        return isValidReservation
    }

    fun onEditReservationButtonPressed() {
        isNewReservation = false
        Router.navigateTo(Screen.NewReservationScreen)
    }

    fun onNewReservationButtonPressed() {
        reservationsItemIndex = -1
        isNewReservation = true
        Router.navigateTo(Screen.NewReservationScreen)
    }

    fun onDeleteReservationButtonPressed() = viewModelScope.launch {
        //delete reservationsListOf[itemIndex]
        //remove It from Room
        try {
            repo.deleteThisReservation(reservationsListOf[reservationsItemIndex])
            reservationsListOf.removeAt(reservationsItemIndex)
            isNewReservation = true
        } catch (ex: Exception){ errorMessage = R.string.errorhasoccurred.toString() }
        Router.navigateTo(Screen.ReservationsScreen)
    }

    fun onReservationListItemClicked(index: Int) {
        reservationsItemIndex = index
        Router.navigateTo(Screen.ReservationDetailsScreen)
    }


    private suspend fun buildReservationList()= viewModelScope.launch {
        try {
            reservationsListOf = repo.getReservationsFor(myProfile.id) as MutableList<Reservation>
        } catch (ex: java.lang.Exception){
            errorMessage = R.string.errorhasoccurred.toString()
        }
        if (reservationsListOf.isEmpty()){
            // addDummyItem(emptyList<Object>): List<Object> where .size() == 1
            reservationsListOf.add(dummyReservation)
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
            errorMessage = R.string.errorhasoccurred.toString()
        }
        if (logSheetList.isEmpty()){
            //add dummy item to list
            logSheetList.add(dummyLogSheet)
        }
    }

    fun onLogSheetItemClicked(index: Int)= viewModelScope.launch {
        logSheetItem = logSheetList[index]
        logsheetItemIndex = index
        when (logSheetItem.gameType) {
            GameType().DND -> {
                try {
                    dndLogSheets.forEach{ logsheet->
                        if (logSheetItem.id==logsheet.id){
                            dndLogSheetIndex = dndLogSheets.indexOf(logsheet)
                        }
                    }
                    dndLogSheet = dndLogSheets[dndLogSheetIndex]
                    dndLogSheetEntries = repo.getAllDndEntriesForThisPC(myProfile.id,logSheetItem.id) as MutableList<DndAlEntry>
                    isNewDndLogSheet = false
                    errorMessage = R.string.none.toString()
                    Router.navigateTo(Screen.DndLogSheetScreen)
                } catch (ex: Exception){
                    errorMessage = R.string.errorhasoccurred.toString()
                }
            }
            GameType().MTG -> {
                try {
                    mtgLogSheets.forEach{ logsheet ->
                        if (logSheetItem.id==logsheet.id){
                            mtgLogSheetIndex = mtgLogSheets.indexOf(logsheet)
                        }
                    }
                    mtgLogSheet = mtgLogSheets[mtgLogSheetIndex]
                    mtgLogSheetEntries = repo.getAllMtgEntriesFor(myProfile.id,logSheetItem.id) as MutableList<MtgEntry>
                    myPlayers = repo.getPlayerObjectForThisGame(myProfile.id,mtgLogSheet.id)!!
                    playersList = myPlayers.members.split(";") as MutableList<String>
                    isNewMtgLogSheet = false
                    errorMessage = R.string.none.toString()
                    Router.navigateTo(Screen.MtgLogSheetScreen)
                } catch (ex: Exception){
                    errorMessage = R.string.errorhasoccurred.toString()
                }
            }
            GameType().MONOP -> {
                Router.navigateTo(Screen.LogSheetsScreen)
                errorMessage = R.string.comingsoon.toString()
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
        isNewDndLogSheet = true
        dndLogSheetEntries.clear()
        dndEntryItemIndex = -1
        dndLogSheetItemIndex = -1
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }

    fun onNewMtgLogSheetButtonPressed() {
        isNewMtgLogSheet = true
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

    private val _levelaccepted = MutableLiveData("")
    val levelaccepted: LiveData<String> = _levelaccepted
    fun onLevelAcceptedChange(yn: String) {
        _levelaccepted.value = yn
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
    private fun setClassesRaw(newClass: String,logsheetID: Int){
        var classesRaw = ""
        classesRaw += newClass
        for (i in 0 until dndLogSheets.size) {
            if (dndLogSheets[i].id == logsheetID) {
                dndLogSheets[i].classes = classesRaw
            }
        }
    }
    private fun getStartingLevel(logsheetID: Int): String {
        var latestEntry = dndLogSheetEntryBlank
        var firstEntry = dndLogSheetEntries[0]
        var dnd = dndLogSheetBlank
        //create List of classes and count members
        var classesRaw = ""
        dndLogSheets.forEach { logsheet ->
            if (logsheet.id == logsheetID) {
                dnd = logsheet
                classesRaw = logsheet.classes
            }
        }
        val classesList = classesRaw.split(";")
        var startinglevel = classesList.count()
        var startlevel = ""

        dndLogSheetEntries.forEach { entry ->
            if (entry.id == logsheetID) {
                if (entry.dayMonthYear > firstEntry.dayMonthYear) {
                    latestEntry = entry
                    firstEntry = entry
                }
            }
        }
        when (latestEntry.newClassLevel) {
            "Barbarian" -> {
                startinglevel++
                classesRaw += "Barbarian;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Cleric" -> {
                startinglevel++
                classesRaw += "Cleric;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Fighter" -> {
                startinglevel++
                classesRaw += "Fighter;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Monk" -> {
                startinglevel++
                classesRaw += "Monk;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Paladin" -> {
                startinglevel++
                classesRaw = classesRaw + "Paladin"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Rogue" -> {
                startinglevel++
                classesRaw += "Rogue;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Sorcerer" -> {
                startinglevel++
                classesRaw += "Sorcerer;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Wizard" -> {
                startinglevel++
                classesRaw += "Wizard;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
        }
        startlevel = startinglevel.toString()
        return startlevel
    }

    private fun getStartingGold(logsheetID: Int): String {
        var startgold = ""
        var startinggold = 0
        var latestEntry = dndLogSheetEntryBlank
        var firstEntry = dndLogSheetEntries[0]
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID == logsheetID) {
                if (entry.dayMonthYear > firstEntry.dayMonthYear) {
                    latestEntry = entry
                    firstEntry = entry
                }
            }
        }
        startgold = latestEntry.newGoldTotal
        return startgold
    }

    private fun getStartingDowntime(logsheetID: Int): String {
        var startdowntime = ""
        var startingdowntime = 0
        var latestEntry = dndLogSheetEntryBlank
        var firstEntry = dndLogSheetEntries[0]
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID == logsheetID) {
                if (entry.dayMonthYear > firstEntry.dayMonthYear) {
                    latestEntry = entry
                    firstEntry = entry
               }
            }
        }
        startdowntime = latestEntry.newDowntimeTotal
        return startdowntime
    }

    fun getStartingMagicItems(logsheetID: Int): String {
        var startmagicitems = ""
        var startingmagicitems = 0
        var latestEntry = dndLogSheetEntryBlank
        var firstEntry = dndLogSheetEntries[0]
        //dndLogSheetEntries = getDndEntries(logsheetID)
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID == logsheetID) {
                if (entry.dayMonthYear > firstEntry.dayMonthYear) {
                    latestEntry = entry
                    firstEntry = entry
                }
            }
        }
        startmagicitems = latestEntry.newPermanentMagicItemTotal
        return startmagicitems
    }


    fun onEditDndLogSheetButtonPressed() {
        isNewDndLogSheet = false
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }

    fun onDeleteDndLogSheetButtonPressed()= viewModelScope.launch {
        isNewDndLogSheet = true
        repo.deleteThisDndLogSheet(dndLogSheets[dndLogSheetIndex])
        repo.deleteThisLogSheet(logSheetList[logsheetItemIndex])
        repo.deleteAllDndEntriesFor(myProfile.id,dndLogSheets[dndLogSheetIndex].id)
        dndLogSheets.removeAt(dndLogSheetIndex)
        logSheetList.removeAt(logsheetItemIndex)
        dndLogSheetEntries.clear()
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    fun onSubmitDndLogSheetButtonPressed() = viewModelScope.launch{
        var newDndLogSheet = dndLogSheetBlank
        var newLogSheet = dummyLogSheet
        // build a DndAlLogSheet object
        // insert it into Room
        // and add it to dndLogSheets
        try {
            if (isNewDndLogSheet) { //new logsheet add to dndLogSheets and logsheets
                // create new LogSheet
                newLogSheet.gameType = GameType().DND
                newLogSheet.profileID = myProfile.id
                newLogSheet.dateCreated = dateCreated()
                // add it to Room
                newLogSheet.id = repo.addNewLogSheet(newLogSheet).toInt()
                // use it.id to create child logsheets
                newDndLogSheet.id = newLogSheet.id
                newDndLogSheet.gameType = newLogSheet.gameType
                newDndLogSheet.profileID = newLogSheet.profileID
                newDndLogSheet.playerDCInumber = playerdcinumber.value.toString()
                newDndLogSheet.characterName = charactername.value.toString()
                newDndLogSheet.characterRace = characterrace.value.toString()
                newDndLogSheet.classes = classes.value.toString()
                newDndLogSheet.faction = faction.value.toString()
                repo.addNewDndLogSheet(newDndLogSheet)
                dndLogSheets.add(newDndLogSheet)
                logSheetList.add(newLogSheet)
            } else { // old logsheet edit at logsheetItemIndex
                if (!playerdcinumber.equals("")) {
                    dndLogSheets[dndLogSheetIndex].playerDCInumber = playerdcinumber.value.toString()
                }
                if (!charactername.equals("")) {
                    dndLogSheets[dndLogSheetIndex].characterName = charactername.value.toString()
                }
                if (!characterrace.equals("")) {
                    dndLogSheets[dndLogSheetIndex].characterRace = characterrace.value.toString()
                }
                if (!faction.equals("")) {
                    dndLogSheets[dndLogSheetIndex].faction = faction.value.toString()
                }
                repo.updateThisDndLogSheet(dndLogSheets[dndLogSheetIndex])
            }
            Router.navigateTo(Screen.LogSheetsScreen)
        } catch (ex: Exception){
            errorMessage = R.string.errorhasoccurred.toString()
        }
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    // use Room to build this list
    var dndLogSheets = mutableListOf<DndAlLogSheet>()
    var dndLogSheetIndex = -1
    var logsheet_id = 0
    private fun buildDndLogSheets() = viewModelScope.launch {
        try {
            dndLogSheets = repo.getAllDndLogSheetsFor(myProfile.id) as MutableList<DndAlLogSheet>
        } catch (ex: Exception){
            errorMessage = R.string.errorhasoccurred.toString()
        }
    }

    // user Room to build this list
    var dndLogSheetEntries = mutableListOf<DndAlEntry>()
    var isNewDndEntry = false

    fun onDndEntryItemClicked(index: Int) {
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
        isNewDndEntry = true
        dndEntryItemIndex = -1
        Router.navigateTo(Screen.EditDNDentryScreen)
    }

    fun onEditDndEntryButtonPressed() {
        isNewDndEntry = false
        Router.navigateTo(Screen.EditDNDentryScreen)
    }

    fun onDeleteDndEntryButtonPressed() = viewModelScope.launch{
        //only allow delete of most recent entry
        dndLogSheetEntries.removeAt(dndEntryItemIndex)
        repo.deleteThisDndEntry(dndLogSheetEntries[dndEntryItemIndex])
        Router.navigateTo(Screen.DndLogSheetScreen)
    }

    fun onSubmitDndEntryButtonPressed() = viewModelScope.launch{
        var newEntry = dndLogSheetEntryBlank
        //get data from viewModel variables
        //change object in list
        //change Room
        if (isNewDndEntry) {// make a new entry and add it to the list
            newEntry.logsheetID = logsheet_id
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
                newEntry.startingLevel = getStartingLevel(dndLogSheet.id)
                newEntry.startingGold = getStartingGold(dndLogSheet.id)//get total from last entry
                newEntry.startingDowntime =
                    getStartingDowntime(dndLogSheet.id)// get total from last entry
                newEntry.startingPermanentMagicItems =
                    getStartingMagicItems(dndLogSheet.id)// get total from last entry
            }
            newEntry.levelAccepted = "Y"
            newEntry.goldPlusMinus = goldplusminus.value.toString()
            newEntry.downtimePlusMinus = downtimeplusminus.value.toString()
            newEntry.permanentMagicItemsPlusMinus = permanentmagicitemsplusminus.value.toString()
            newEntry.newClassLevel = newclass.value.toString()
            newEntry.newGoldTotal = getTotalGold(newEntry.startingGold, newEntry.goldPlusMinus)
            newEntry.newDowntimeTotal =
                getTotalDowntime(newEntry.startingDowntime, newEntry.downtimePlusMinus)
            newEntry.newPermanentMagicItemTotal = getTotalPermanentMagicItems(
                newEntry.startingPermanentMagicItems,
                newEntry.permanentMagicItemsPlusMinus
            )
            newEntry.adventureNotes = advnotes.value.toString()
            repo.addNewDndEntry(newEntry)
            dndLogSheetEntries.add(newEntry)
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
            repo.updateThisDndEntry(dndLogSheetEntries[dndEntryItemIndex])
        }
        Router.navigateTo(Screen.DndLogSheetScreen)
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



    private fun buildMtgLogSheets() = viewModelScope.launch {
       try {
           mtgLogSheets = repo.getAllMtgLogSheetsFor(myProfile.id) as MutableList<MTGlogsheet>
       } catch (ex: Exception) {
           errorMessage = R.string.errorhasoccurred.toString()
       }
    }

    fun onNewMtgLogSheetEntryButtonPressed() {
        mtgEntryItemIndex = -1
        isNewMtgEntry = true
        newPlayersList.clear()
        Router.navigateTo(Screen.EditMTGentryScreen)
    }

    fun onEditMtgLogSheetButtonPressed() {
        isNewMtgLogSheet = false
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }

    fun onDeleteMtgLogSheetButtonPressed()= viewModelScope.launch{
        repo.deleteThisMtgLogSheet(mtgLogSheets[mtgLogSheetIndex])
        repo.deleteThisLogSheet(logSheetList[logsheetItemIndex])
        mtgLogSheets.removeAt(mtgLogSheetIndex)
        logSheetList.removeAt(logsheetItemIndex)
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    fun onAddPlayerButtonPressed(players: String) {
        isAddingNewPlayers = true
        newPlayersList = players.split("\r").toMutableList()
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }

    fun onSubmitMtgLogSheetButtonPressed() = viewModelScope.launch{
        var newMtgLogSheet = mtgLogSheetBlank
        var newPlayers = playersBlank
        var newLogSheet = dummyLogSheet
        val separator = ";"
        //playersString = newPlayers.value.toString()
        try {
            // create a LogSheet object
            if (isNewMtgLogSheet){
                newLogSheet.gameType = GameType().MTG
                newLogSheet.profileID = myProfile.id
                newLogSheet.dateCreated = dateCreated()
                newMtgLogSheet.id = repo.addNewLogSheet(newLogSheet).toInt()
                newPlayers.logsheetID = newMtgLogSheet.id
                newPlayers.profileID = myProfile.id
                newPlayers.members = newPlayersList.joinToString(separator)
                newMtgLogSheet.playersID = repo.addNewPlayersForANewGame(newPlayers).toInt()
                newMtgLogSheet.dayMonthYear = dateCreated()
                newMtgLogSheet.gameType = newLogSheet.gameType
                newMtgLogSheet.profileID = newPlayers.profileID
                repo.addNewMtgLogSheet(newMtgLogSheet)
                mtgLogSheets.add(newMtgLogSheet)
            } else {
                newPlayers = repo.getPlayerObjectForThisGame(myProfile.id,mtgLogSheets[mtgLogSheetIndex].id)!!
                newPlayers.members = newPlayersList.joinToString(separator)
                repo.updatePlayersForThisGame(newPlayers)
                mtgLogSheets[mtgLogSheetIndex].playersID = newPlayers.id
                repo.updateThisMtgLogSheet(mtgLogSheets[mtgLogSheetIndex])
            }
            isAddingNewPlayers = false
        } catch (ex: java.lang.Exception){
            errorMessage = R.string.errorhasoccurred.toString()
        }
        Router.navigateTo(Screen.LogSheetsScreen)
    }
    //new mtg entry item
    var isNewMtgEntry = false

    fun onMtgEntryItemClicked(index: Int, count: Int) {
        mtgEntryItemIndex = index
        magicGameNumber = count
        mtgLogSheetEntry = mtgLogSheetEntries[index]
        Router.navigateTo(Screen.MtgEntryScreen)
    }

    fun onEditMtgEntryButtonPressed() {
        isNewMtgEntry = false
        Router.navigateTo(Screen.EditMTGentryScreen)
    }

    fun onDeleteMtgEntryButtonPressed() = viewModelScope.launch{
        //delete from Room
        repo.deleteThisMtgEntry(mtgLogSheetEntry)
        // and local list
        mtgLogSheetEntries.removeAt(mtgEntryItemIndex)
        Router.navigateTo(Screen.MtgLogSheetScreen)
    }

    fun onSubmitMtgEntryButtonPressed()= viewModelScope.launch {
        var newMagicEntry = mtgLogSheetEntryBlank
        if(isNewMtgEntry){
            // create a mtgEntry object
            newMagicEntry.dayMonthYear = dateCreated()
            newMagicEntry.logsheetID = logSheetItem.id
            newMagicEntry.profileID = myProfile.id
            newMagicEntry.winner = winner.toString().replace("\r",";")
            // add it to Room
            newMagicEntry.id = repo.addNewMtgEntry(newMagicEntry).toInt()
            // add it to mtgEntriesList
            mtgLogSheetEntries.add(newMagicEntry)
            Router.navigateTo(Screen.MtgLogSheetScreen)
        } else {
            // update the mtgEntry object
                mtgLogSheetEntry.winner =
                    winner.toString().replace("\r",";")
            // update the mtgEntriesList
            mtgLogSheetEntries[mtgEntryItemIndex].winner =
                winner.toString().replace("\r",";")
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