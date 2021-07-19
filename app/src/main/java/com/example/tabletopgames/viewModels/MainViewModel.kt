package com.example.tabletopgames.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopgames.R
import com.example.tabletopgames.models.*
import com.example.tabletopgames.views.Router
import com.example.tabletopgames.views.Screen
import io.realm.Realm
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

open class MainViewModel() : ViewModel() {
    // Realm Stuff
    val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

    // helper
    var reservationsItemIndex = -1
    fun getImg(gameType: String): Int {
        return when (gameType) {
            GameType().DND -> R.drawable.dungeonsndragons
            GameType().MTG -> R.drawable.magicthegathering
            GameType().MONOP -> R.drawable.monopoly
            else -> R.drawable.multiverselogo
        }
    }

    private fun getMonthName(month: String): String {
        var monthName = "January"
        monthName = when (month) {
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
        return monthName
    }

    private fun getNewID(): String {
        return Calendar.getInstance().timeInMillis.toString() +
                " " + UUID.randomUUID().toString()
    }

    private var currentDate = LocalDateTime.now()
    private var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    private var today_f = currentDate.format(formatter)
    fun getToday(): String {
        return today_f
    }


    //generic navigation functions
    fun backButton() {
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

    fun onHomeButtonPressed() {
        Router.navigateTo(Screen.HomeScreen)
    }

    //login stuff
    val loginBlank = LoginModel("", "", "")
    val testLogin = LoginModel("1", "test@gmail.com", "password")
    var myLogin = testLogin
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginPressed() {
        var newProfile = profileBlank
        var foundLogin = loginBlank
        var found = false
        //search Realm for email/password
        //goto create profile if not found

        //foundLogin = login realm where email == email
        // newProfile = profile realm where email == Profile.email

        found = false//for testing

        if (found) {
            /* this is for testing */

            myProfile = newProfile
            myLogin = foundLogin
            Router.navigateTo(Screen.HomeScreen)
        } else {
            Router.navigateTo(Screen.EditProfileScreen)
        }
    }

    private fun addNewLogin(email: String, password: String) {

        // create a Login object
        val newLogin = LoginModel(getNewID(), email, password)

        // on below line we are calling a method to execute a transaction.
        realm.executeTransactionAsync { realm -> // inside on execute method we are calling a method
            // to copy to realm database from our modal class.
            realm.copyToRealm(newLogin)
        }

    }

    //profile stuff
    val profileBlank = Profile("", "", "", "", "")
    var newProfile = -1
    val testProfile = Profile(
        "1", "Test", "Profile",
        "test@gmail.com", "8178675309"
    )

    // use RealmResults to get this profile
    var myProfile = testProfile

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

    fun getProfile(): Profile {
        return myProfile
    }

    fun onEditProfilePressed() {
        newProfile = 1
        Router.navigateTo(Screen.EditProfileScreen)
    }

    fun onCreateProfilePressed() {
        myLogin = LoginModel("", "", "Password")
        myProfile = Profile(
            "", "First Name",
            "Last Name", "Email", "Phone"
        )
        Router.navigateTo(Screen.EditProfileScreen)
    }

    fun onSubmitProfilePressed() {
        //add new if creating profile
        //update this if editing profile
        if (email.value.toString() != "") {
            myLogin.email = email.value.toString()
            myProfile.email = email.value.toString()
        }
        if (password.value.toString() != "")
            myLogin.password = password.value.toString()
        if (firstName.value.toString() != "")
            myProfile.firstName = firstName.value.toString()
        if (lastName.value.toString() != "")
            myProfile.lastName = lastName.value.toString()
        if (phone.value.toString() != "")
            myProfile.phone = phone.value.toString()
        newProfile = 1
        Router.navigateTo(Screen.MyProfileScreen)
    }

    //reservation stuff
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
        "", "", "",
        "", "", "", "", ""
    )
    private var newReservation = reservationBlank

    //use RealmResults to build this list
    // where profileID == MyProfile.id
    val reservationsListOf = mutableListOf<Reservation>()

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

    private fun getNewResYear(day: String, month: String): String {
        val cal = Calendar.getInstance()
        val yearV: String

        val today = cal.get(Calendar.DAY_OF_MONTH)
        val thisMonth = cal.get(Calendar.MONTH)
        val thisYear = cal.get(Calendar.YEAR)
        yearV = if (thisMonth == 12 && month == "12" && today <= day.toInt()) {
            cal.add(thisYear, 1).toString()
        } else {
            thisYear.toString()
        }
        return yearV
    }

    fun onSubmitReservationButtonPressed() {
        val dayV = day.value.toString()
        val monthV = month.value.toString()
        val year = getNewResYear(dayV, monthV)
        val dayMonthYear = dayV + " " + getMonthName(monthV) + " " + year
        if (reservationsItemIndex == -1) {
            newReservation.gameType = gametype.value.toString()
            newReservation.dayMonthYear = dayMonthYear
            newReservation.time = time.value.toString()
            newReservation.table = table.value.toString()
            newReservation.seat = seat.value.toString()
            newReservation.duration = duration.value.toString()
            newReservation.profileID = myProfile.id
            newReservation.id = getNewID()

            //if no reservation with this date, time, and location exists
            //then add to Reservation Realm and add to reservationsListOf

            reservationsListOf.add(newReservation)
        } else {
            if (gametype.value != "") {
                reservationsListOf[reservationsItemIndex].gameType = gametype.value.toString()
            }
            if (dayMonthYear != "") {
                reservationsListOf[reservationsItemIndex].dayMonthYear = dayMonthYear
            }
            if (table.value != "") {
                reservationsListOf[reservationsItemIndex].table = table.value.toString()
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
        }
        Router.navigateTo(Screen.ReservationsScreen)
    }

    fun onEditReservationButtonPressed() {
        Router.navigateTo(Screen.NewReservationScreen)
    }

    fun onNewReservationButtonPressed() {
        reservationsItemIndex = -1
        Router.navigateTo(Screen.NewReservationScreen)
    }

    fun onDeleteReservationButtonPressed() {
        //delete reservationsListOf[itemIndex]
        reservationsListOf.removeAt(reservationsItemIndex)
        //remove It from Realm

        Router.navigateTo(Screen.ReservationsScreen)
    }

    fun onReservationListItemClicked(index: Int) {
        reservationsItemIndex = index
        Router.navigateTo(Screen.ReservationDetailsScreen)
    }

    //test data for the ui
    fun buildReservationList() {
        reservationsListOf.add(
            Reservation(
                "1", "1", GameType().DND,
                "1 January 2021", "7:00 PM", "1",
                "1", "1"
            )
        )
        reservationsListOf.add(
            Reservation(
                "2", "1", GameType().DND,
                "2 January 2021", "6:00 PM", "2",
                "2", "2"
            )
        )
        reservationsListOf.add(
            Reservation(
                "3", "1", GameType().MTG,
                "3 January 2021", "5:00 PM", "3",
                "3", "3"
            )
        )
        reservationsListOf.add(
            Reservation(
                "4", "1", GameType().MONOP,
                "4 January 2021", "4:00 PM", "4",
                "4", "4"
            )
        )
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
    var logSheetList = mutableListOf<LogSheet>()
    fun buildLogSheetList() {
        logSheetList.add(LogSheet(getNewID(), myProfile.id, GameType().DND, "1 January 2021"))
        logSheetList.add(LogSheet(getNewID(), myProfile.id, GameType().MTG, "2 January 2021"))
        logSheetList.add(LogSheet(getNewID(), myProfile.id, GameType().MTG, "3 January 2021"))
        val firstID = logSheetList[0].id
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

    var logSheetItemBlank = LogSheet("", "", "", "")
    var logSheetItem = logSheetItemBlank
    var logsheetItemIndex = -1
    var newLogsheet = false

    fun onNewDndLogSheetButtonPressed() {
        newLogsheet = true
        dndLogSheetEntries.clear()
        dndEntryItemIndex = -1
        dndLogSheetItemIndex = -1
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }

    fun onNewMtgLogSheetButtonPressed() {
        newLogsheet = true
        mtgEntryItemIndex = -1
        logsheetItemIndex = -1
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }


    fun onLogSheetItemClicked(index: Int) {
        logSheetItem = logSheetList[index]
        logsheetItemIndex = index
        when (logSheetItem.gameType) {
            GameType().DND -> {
                //dndLogSheet = dndLogSheets where id == logsheetID

                var i = 0
                dndLogSheets.forEach { dndlogsheet ->
                    if (logSheetItem.id == dndlogsheet.id) {
                        dndLogSheet = dndlogsheet
                        dndLogSheetIndex = i
                    }
                    i++
                }
                Router.navigateTo(Screen.DndLogSheetScreen)
            }
            GameType().MTG -> {
                var i = 0
                mtgLogSheets.forEach { mtglogsheet ->
                    if (logSheetItem.id == mtglogsheet.id) {
                        mtgLogSheet = mtglogsheet
                        mtgLogSheetIndex = i
                    }
                    i++
                }
                Router.navigateTo(Screen.MtgLogSheetScreen)
            }
            GameType().MONOP -> {
                Router.navigateTo(Screen.MonopolyLogSheetScreen)
            }
            else -> Router.navigateTo(Screen.HomeScreen)
        }
    }

    var dndLogSheetBlank = DndAlLogSheet(
        "", "", GameType().DND, "",
        "", "", "", "", ""
    )
    var dndLogSheet = dndLogSheetBlank
    var dndLogSheetItemIndex = -1
    var dndEntryItemIndex = -1
    var dndLogSheetEntryBlank = DndAlEntry(
        "", "", "",
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
    private fun setClassesRaw(newClass: String,logsheetID: String){
        var classesRaw = ""
        classesRaw = classesRaw + newClass
        for (i in 0 until dndLogSheets.size) {
            if (dndLogSheets[i].id == logsheetID) {
                dndLogSheets[i].classes = classesRaw
            }
        }
    }
    fun getStartingLevel(logsheetID: String): String {
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
                classesRaw = classesRaw + "Barbarian;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Cleric" -> {
                startinglevel++
                classesRaw = classesRaw + "Cleric;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Fighter" -> {
                startinglevel++
                classesRaw = classesRaw + "Fighter;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Monk" -> {
                startinglevel++
                classesRaw = classesRaw + "Monk;"
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
                classesRaw = classesRaw + "Rogue;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Sorcerer" -> {
                startinglevel++
                classesRaw = classesRaw + "Sorcerer;"
                for (i in 0 until dndLogSheets.size) {
                    if (dndLogSheets[i].id == logsheetID) {
                        dndLogSheets[i].classes = classesRaw
                    }
                }
            }
            "Wizard" -> {
                startinglevel++
                classesRaw = classesRaw + "Wizard;"
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

    fun getStartingGold(logsheetID: String): String {
        var startgold = ""
        var startinggold = 0
        var latestEntry = dndLogSheetEntryBlank
        var firstEntry = dndLogSheetEntries[0]
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID == logsheetID) {
                if (entry.dayMonthYear > firstEntry.dayMonthYear) {
                    latestEntry = entry
                }
            }
        }
        startgold = latestEntry.newGoldTotal
        return startgold
    }

    fun getStartingDowntime(logsheetID: String): String {
        var startdowntime = ""
        var startingdowntime = 0
        var latestEntry = dndLogSheetEntryBlank
        var firstEntry = dndLogSheetEntries[0]
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID == logsheetID) {
                if (entry.dayMonthYear > firstEntry.dayMonthYear) {
                    latestEntry = entry
                }
            }
        }
        startdowntime = latestEntry.newDowntimeTotal
        return startdowntime
    }

    fun getStartingMagicItems(logsheetID: String): String {
        var startmagicitems = ""
        var startingmagicitems = 0
        var latestEntry = dndLogSheetEntryBlank
        var firstEntry = dndLogSheetEntries[0]
        //dndLogSheetEntries = getDndEntries(logsheetID)
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID == logsheetID) {
                if (entry.dayMonthYear > firstEntry.dayMonthYear) {
                    latestEntry = entry
                }
            }
        }
        startmagicitems = latestEntry.newPermanentMagicItemTotal
        return startmagicitems
    }


    fun onEditDndLogSheetButtonPressed() {
        newLogsheet = false
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }

    fun onDeleteDndLogSheetButtonPressed() {
        dndLogSheets.removeAt(dndLogSheetIndex)
        logSheetList.removeAt(logsheetItemIndex)
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    fun onSubmitDndLogSheetButtonPressed() {
        var newDndLogSheet = dndLogSheetBlank
        var newlogsheetitem = logSheetItemBlank
        // build a DndAlLogSheet object and copyToRealm
        // and add to dndLogSheets

        if (newLogsheet) { //new logsheet add to dndLogSheets and logsheets
            newDndLogSheet.playerDCInumber = playerdcinumber.value.toString()
            newDndLogSheet.characterName = charactername.value.toString()
            newDndLogSheet.characterRace = characterrace.value.toString()
            newDndLogSheet.classes = classes.value.toString()
            newDndLogSheet.faction = faction.value.toString()
            newlogsheetitem.profileID = myProfile.id
            newlogsheetitem.gameType = GameType().DND
            newlogsheetitem.dateCreated = today_f
            newDndLogSheet.id = getNewID()
            newlogsheetitem.id = newDndLogSheet.id
            dndLogSheets.add(newDndLogSheet)
            logSheetList.add(newlogsheetitem)
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
            if (!classes.equals("")) {
                dndLogSheets[dndLogSheetIndex].classes = classes.value.toString()
            }
            if (!faction.equals("")) {
                dndLogSheets[dndLogSheetIndex].faction = faction.value.toString()
            }
        }
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    // use RealmResults to build this list
    var dndLogSheets = mutableListOf<DndAlLogSheet>()
    var dndLogSheetIndex = -1
    var logsheet_id = ""
    fun buildDndLogSheets() {
        // use RealmResults to build this list

        logSheetList.forEach { logsheet ->
            if (logsheet.gameType == GameType().DND) {
                logsheet_id = logsheet.id
            }
        }
        dndLogSheets.add(
            DndAlLogSheet(
                logsheet_id, myProfile.id, GameType().DND, "12345678910",
                "Ben Dover", "Human", "fighter;rogue;rogue", "none",
                ""
            )
        )
    }

    // user RealmResults to build this list
    var dndLogSheetEntries = mutableListOf<DndAlEntry>()
    fun buildDndEntries() {
        dndLogSheetEntries = getDndEntries(logsheet_id)
    }

    fun getDndEntries(logsheetID: String): MutableList<DndAlEntry> {
        val dndEntryList = mutableListOf<DndAlEntry>()
        // use RealmResults to build dndLogSheetEntries
        // DndAlEntry where dndLogSheets[dndLogSheetIndex].id = logsheetID

        // test data for ui
        dndEntryList.add(
            DndAlEntry(
                getNewID(),
                myProfile.id,
                logsheetID,
                "DDAL",
                "Test Adventure 1",
                "1 January 2021",
                "Test DM 1",
                "1",
                "0",
                "0",
                "0",
                "Y",
                "1000",
                "3",
                "1",
                "none",
                "1000",
                "3",
                "1",
                "This was a blast!"
            )
        )
        dndEntryList.add(
            DndAlEntry(
                getNewID(),
                myProfile.id,
                logsheetID,
                "DDAL",
                "Test Adventure 2",
                "8 January 2021",
                "Test DM 2",
                "1",
                "1000",
                "3",
                "1",
                "Y",
                "2000",
                "3",
                "1",
                "none",
                "3000",
                "6",
                "2",
                "The second adventure was better than the first!"
            )
        )
        dndEntryList.add(
            DndAlEntry(
                getNewID(),
                myProfile.id,
                logsheetID,
                "DDAL",
                "Test Adventure 3",
                "15 January 2021",
                "Test DM 3",
                "1",
                "3000",
                "6",
                "2",
                "Y",
                "3000",
                "3",
                "1",
                "none",
                "6000",
                "9",
                "3",
                "The third adventure was the best of them all!"
            )
        )
        return dndEntryList
    }

    fun onDndEntryItemClicked(index: Int) {
        dndEntryItemIndex = index
        dndLogSheetEntry = dndLogSheetEntries[index]
        Router.navigateTo(Screen.DndEntryScreen)
    }

    fun getTotalGold(startgold: String, plusminus: String): String {
        val startingGold = startgold.toInt()
        val plusminus = plusminus.toInt()
        val goldtotal = startingGold + plusminus
        return goldtotal.toString()
    }

    fun getTotalDowntime(startdowntime: String, plusminus: String): String {
        val startingDowntime = startdowntime.toInt()
        val plusminus = plusminus.toInt()
        val downtimetotal = startingDowntime + plusminus
        return downtimetotal.toString()
    }

    fun getTotalPermanentMagicItems(startmagicitems: String, plusminus: String): String {
        val startmagicitems = startmagicitems.toInt()
        val plusminus = plusminus.toInt()
        val magicitemstotal = startmagicitems + plusminus
        return magicitemstotal.toString()
    }

    fun onNewDndEntryButtonPressed() {
        dndEntryItemIndex = -1
        Router.navigateTo(Screen.EditDNDentryScreen)
    }

    fun onEditDndEntryButtonPressed() {
        Router.navigateTo(Screen.EditDNDentryScreen)
    }

    fun onDeleteDndEntryButtonPressed() {
        //only allow delete of most recent entry
        dndLogSheetEntries.removeAt(dndEntryItemIndex)
        Router.navigateTo(Screen.DndLogSheetScreen)
    }

    fun onSubmitDndEntryButtonPressed() {
        var newEntry = dndLogSheetEntryBlank
        //get data from viewModel variables
        //change object in list
        //change RealmObject
        if (dndEntryItemIndex == -1) {// make a new entry and add it to the list
            newEntry.logsheetID = logsheet_id
            newEntry.adventureName = advname.value.toString()
            newEntry.adventureCode = advcode.value.toString()
            newEntry.profileID = myProfile.id
            newEntry.dayMonthYear = getToday()
            newEntry.dmDCInumber = dmdcinumber.value.toString()
            if (newLogsheet) {
                newEntry.startingLevel = classes.value.toString()
                newEntry.startingGold = (0).toString()
                newEntry.startingDowntime = (0).toString()
                newEntry.startingPermanentMagicItems = (0).toString()
                newLogsheet = false
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
            newEntry.id = getNewID()
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
        }
        Router.navigateTo(Screen.DndLogSheetScreen)
    }

    //mtg log sheet stuff
    //use RealmResults to build this list
    var mtgLogSheetBlank = MTGlogsheet(
        "", "", GameType().MTG,
        "", "", ""
    )
    var mtgLogSheets = mutableListOf<MTGlogsheet>()
    var mtgLogSheetEntries = mutableListOf<MtgEntry>()
    var mtgLogSheetEntryBlank = MtgEntry("", "", "")
    var mtgLogSheetEntry = mtgLogSheetEntryBlank
    var mtgLogSheet = mtgLogSheetBlank
    var mtgLogSheetIndex = -1
    var mtgEntryItemIndex = -1
    private var _winner = MutableLiveData("")
    var winner: LiveData<String> = _winner
    fun onWinnerChange(winner: String) {
        _winner.value = winner
    }

    private var _dateCreated = MutableLiveData("")
    var dateCreated: LiveData<String> = _dateCreated
    fun onDateCreatedChange(datecreated: String) {
        _dateCreated.value = datecreated
    }

    fun buildMtgLogSheets() {
        mtgLogSheets.add(
            MTGlogsheet(
                logSheetList[1].id, myProfile.id, GameType().MTG,
                players, "2 January 2021", mtgEntries
            )
        )
        mtgLogSheets.add(
            MTGlogsheet(
                logSheetList[2].id, myProfile.id, GameType().MTG,
                players, "3 January 2021", mtgEntries
            )
        )

    }

    var mtgEntries = ""
    fun onNewMtgLogSheetEntryButtonPressed() {
        mtgEntryItemIndex = -1
        newPlayersList.clear()
        Router.navigateTo(Screen.EditMTGentryScreen)
    }

    fun onEditMtgLogSheetButtonPressed() {
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }

    fun onDeleteMtgLogSheetButtonPressed() {
        mtgLogSheets.removeAt(mtgLogSheetIndex)
        logSheetList.removeAt(logsheetItemIndex)
        Router.navigateTo(Screen.LogSheetsScreen)
    }

    fun onSubmitMtgLogSheetButtonPressed() {

        Router.navigateTo(Screen.LogSheetsScreen)
    }

    fun buildMtgLogSheetEntries() {
        //use RealmResults to build this list
        // get all mtg log sheet entries where logsheetID==logsheet.id

        // test data for ui
        for (i in 0 until 1) {
            mtgLogSheetEntries.add(
                MtgEntry(
                    getNewID(),
                    mtgLogSheets[i].id,
                    testListOfPlayers[i + 1]
                )
            )
            mtgLogSheetEntries.add(MtgEntry(getNewID(), mtgLogSheets[i].id, testListOfPlayers[i]))
            mtgLogSheetEntries.add(
                MtgEntry(
                    getNewID(),
                    mtgLogSheets[i].id,
                    testListOfPlayers[i + 2]
                )
            )
            mtgLogSheetEntries.add(
                MtgEntry(
                    getNewID(),
                    mtgLogSheets[i].id,
                    testListOfPlayers[i + 1]
                )
            )
        }

        mtgLogSheetEntries.forEach { entry ->
            mtgEntries = entry.id + ":" + entry.logsheetID + ";"
        }
    }

    var players = ""
    var magicGameNumber = 1
    var testListOfPlayers = mutableListOf<String>()
    var playersListEmpty = mutableListOf<String>()
    var newPlayersList = playersListEmpty
    fun buildPlayersList() {
        testListOfPlayers.add("Jon Donnson")
        testListOfPlayers.add("Judy Patutti")
        testListOfPlayers.add("Claude Bawls")
        testListOfPlayers.add("I.P. Freehly")
        testListOfPlayers.forEach { player ->
            players += player + ";"
        }
    }

    //new mtg entry item
    val _newPlayer = MutableLiveData("")
    val newPlayer: LiveData<String> = _newPlayer
    fun onPlayerChange(newplayer: String) {
        _newPlayer.value = newplayer
    }

    fun onAddPlayerButtonPressed(player: String) {
        newPlayersList.add(player)
    }

    fun onMtgEntryItemClicked(index: Int, count: Int) {
        mtgEntryItemIndex = index
        magicGameNumber = count
        mtgLogSheetEntry = mtgLogSheetEntries[index]
        Router.navigateTo(Screen.MtgEntryScreen)
    }

    fun onEditMtgEntryButtonPressed() {
        Router.navigateTo(Screen.EditMTGentryScreen)
    }

    fun onDeleteMtgEntryButtonPressed() {
        //delete from Realm and local list
        mtgLogSheetEntries.removeAt(mtgEntryItemIndex)
        Router.navigateTo(Screen.MtgLogSheetScreen)
    }

    fun onSubmitMtgEntryButtonPressed() {
        //update Realm and mtgLogSheetEntries
        mtgLogSheetEntries[mtgEntryItemIndex].winner = winner.toString()
        Router.navigateTo(Screen.MtgLogSheetScreen)
    }
}