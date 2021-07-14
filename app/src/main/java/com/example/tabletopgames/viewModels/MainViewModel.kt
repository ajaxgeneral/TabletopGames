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
    val realm: Realm by lazy{
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
            else -> "January"
        }
        return monthName
    }
    private fun getNewID(): String {return Calendar.getInstance().timeInMillis.toString() +
                " " + UUID.randomUUID().toString()
    }
    private var currentDate = LocalDateTime.now()
    private var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    private var today_f = currentDate.format(formatter)
    private var dayCreated = ""
    private var monthCreated = ""
    private var yearCreated = ""
    private var today = ""



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
        if (email.value.toString() != ""){
            myLogin.email = email.value.toString()
            myProfile.email = email.value.toString()
        }
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


    var reservationBlank = Reservation("","","",
        "","","","","")
    private var newReservation = reservationBlank

    //use RealmResults to build this list
    // where profileID == MyProfile.id
    val reservationsListOf = mutableListOf<Reservation>()

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

    private fun getNewResYear(day: String, month: String) : String{
        val cal = Calendar.getInstance()
        val yearV: String

        val today = cal.get(Calendar.DAY_OF_MONTH)
        val thisMonth = cal.get(Calendar.MONTH)
        val thisYear = cal.get(Calendar.YEAR)
        yearV = if (thisMonth==12 && month=="12" && today <= day.toInt()){
            cal.add(thisYear, 1).toString()
        } else {
            thisYear.toString()
        }
        return yearV
    }
    fun onSubmitReservationButtonPressed(){
        val dayV = day.value.toString()
        val monthV = month.value.toString()
        val year = getNewResYear(dayV,monthV)
        val dayMonthYear = dayV + " " + getMonthName(monthV) + " " + year
        if (reservationsItemIndex==-1){
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
        }
        else {
            reservationsListOf[reservationsItemIndex].gameType = gametype.value.toString()
            reservationsListOf[reservationsItemIndex].dayMonthYear = dayMonthYear
            reservationsListOf[reservationsItemIndex].table = table.value.toString()
            reservationsListOf[reservationsItemIndex].seat = seat.value.toString()
            reservationsListOf[reservationsItemIndex].duration = duration.value.toString()
            reservationsListOf[reservationsItemIndex].time = time.value.toString()
        }
        Router.navigateTo(Screen.ReservationsScreen)
    }
    fun onEditReservationButtonPressed(){
        Router.navigateTo(Screen.NewReservationScreen)
    }
    fun onNewReservationButtonPressed(){
        reservationsItemIndex = -1
        Router.navigateTo(Screen.NewReservationScreen)
    }
    fun onDeleteReservationButtonPressed(){
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
    var logSheetList = mutableListOf<LogSheet>()
    fun buildLogSheetList(){
        logSheetList.add(LogSheet("1",myProfile.id,GameType().DND,"1 January 2021"))
        logSheetList.add(LogSheet("2",myProfile.id,GameType().MTG,"2 January 2021"))
        logSheetList.add(LogSheet("3",myProfile.id,GameType().MONOP,"3 January 2021"))
    }

    //dnd log sheet text field values
    private val _playerdcinumber = MutableLiveData("")
    val playerdcinumber: LiveData<String> = _playerdcinumber
    fun onPlayerDCInumberChanged(playerDCInumber: String){
        _playerdcinumber.value = playerDCInumber
    }
    private val _charactername = MutableLiveData("")
    val charactername: LiveData<String> = _charactername
    fun onCharacterNameChange(name: String){
        _charactername.value = name
    }
    private val _characterrace = MutableLiveData("")
    val characterrace: LiveData<String> = _characterrace
    fun onCharacterRaceChange(race: String){
        _characterrace.value = race
    }
    private val _classes = MutableLiveData("")
    val classes: LiveData<String> = _classes
    fun onClassChange(classes: String){
        _classes.value = classes
    }
    //dndAlLogSheet.classes is calculated from the entries; the default is 'Fighter'

    private val _faction = MutableLiveData("")
    val faction: LiveData<String> = _faction
    fun onFactionChange(faction: String){
        _faction.value = faction
    }

    var logSheetItemBlank = LogSheet("","","","")
    var logSheetItem = logSheetItemBlank
    var logsheetItemIndex = -1

    fun onNewDndLogSheetButtonPressed(){
        dndLogSheetItemIndex = -1
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }
    fun onNewMtgLogSheetButtonPressed(){
        logsheetItemIndex = -1
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }
    fun onNewMonopolyLogSheetButtonPressed(){
        logsheetItemIndex = -1
        Router.navigateTo(Screen.EditMonopLogSheetScreen)
    }

    fun onLogSheetItemClicked(index: Int){
        logSheetItem = logSheetList[index]
        logsheetItemIndex = index
        when(logSheetItem.gameType){
            GameType().DND -> {
                //dndLogSheet = dndLogSheets where id == logsheetID
                var i = 0
                dndLogSheets.forEach{ dndlogsheet ->

                    if (logSheetItem.id==dndlogsheet.id){
                        dndLogSheet = dndlogsheet
                        dndLogSheetIndex = i
                    }
                    i++
                }
                Router.navigateTo(Screen.DndLogSheetScreen)
            }
            GameType().MTG -> {
                var i = 0
                mtgLogSheets.forEach{ mtglogsheet ->
                    if(logSheetItem.id == mtglogsheet.id){
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

    var dndLogSheetBlank = DndAlLogSheet("","",GameType().DND,"",
    "","","","","")
    var dndLogSheet = dndLogSheetBlank
    var dndLogSheetItemIndex = -1
    var dndEntryItemIndex = -1
    var dndLogSheetEntryBlank = DndAlEntry("","","",
        "","","",
        "","","","",
        "","","",
        "","","",
        "","","",
        "")
    var dndLogSheetEntriesEmpty = mutableListOf<DndAlEntry>()
    var dndLogSheetEntry = dndLogSheetEntryBlank
    private val _advname = MutableLiveData("")
    val advname: LiveData<String> = _advname
    fun onAdvNameChange(name: String){
        _advname.value = name
    }
    private val _advcode = MutableLiveData("")
    val advcode: LiveData<String> = _advcode
    fun onAdvCodeChange(code: String){
        _advcode.value = code
    }
    private val _dayMonthYear = MutableLiveData("")
    val dayMonthYear: LiveData<String> = _dayMonthYear
    fun onDayMonthYearChange(date: String){
        _dayMonthYear.value = date
    }
    private val _dmdcinumber = MutableLiveData("")
    val dmdcinumber: LiveData<String> = _dmdcinumber
    fun onDmDciNumberChange(number: String){
        _dmdcinumber.value = number
    }
    private val _levelaccepted = MutableLiveData("")
    val levelaccepted: LiveData<String> = _levelaccepted
    fun onLevelAcceptedChange(yn: String){
        _levelaccepted.value = yn
    }
    private val _goldplusminus = MutableLiveData("")
    val goldplusminus: LiveData<String> = _goldplusminus
    fun onGoldPlusMinusChange(gold: String){
        _goldplusminus.value = gold
    }
    private val _downtimeplusminus = MutableLiveData("")
    val downtimeplusminus: LiveData<String> = _downtimeplusminus
    fun onDowntimePlusMinusChange(downtime: String){
        _downtimeplusminus.value = downtime
    }
    private  val _permanentmagicitemsplusminus = MutableLiveData("")
    val permanentmagicitemsplusminus: LiveData<String> = _permanentmagicitemsplusminus
    fun onPermanentMagicItemsPlusMinusChange(magicitems: String){
        _permanentmagicitemsplusminus.value = magicitems
    }
    private val _newclass = MutableLiveData("")
    val newclass: LiveData<String> = _newclass
    fun onNewClassChange(newclass: String){
        _newclass.value = newclass
    }
    private  val _advnotes = MutableLiveData("")
    val advnotes: LiveData<String> = _advnotes
    fun onAdvNotesChange(notes: String){
        _advnotes.value = notes
    }
    fun getStartingLevel(logsheetID: String): String {
        //create List of classes and count members
        var classesRaw = ""
        dndLogSheets.forEach { logsheet ->
            if (logsheet.id==logsheetID){ classesRaw = logsheet.classes }
        }
        var classesList = classesRaw.split(";")
        val startinglevel = classesList.count()
        var startlevel = ""
        if (startinglevel<=1){ startlevel = "1" }
        else { startlevel = startinglevel.toString() }
        return startlevel
    }
    fun getStartingGold(logsheetID: String): String{
        var startgold = ""
        var startinggold = 0
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID==logsheetID && entry.startingGold!=""){
                startinggold += entry.startingGold.toInt() }
        }
        startgold = startinggold.toString()
        return startgold
    }
    fun getStartingDowntime(logsheetID: String): String{
        var startdowntime = ""
        var startingdowntime = 0
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID==logsheetID && entry.startingGold!=""){
                startingdowntime += entry.startingGold.toInt() }
        }
        startdowntime = startingdowntime.toString()
        return startdowntime
    }
    fun getStartingMagicItems(logsheetID: String): String{
        var startmagicitems = ""
        var startingmagicitems = 0
        dndLogSheetEntries.forEach { entry ->
            if (entry.logsheetID==logsheetID && entry.startingGold!=""){
                startingmagicitems += entry.startingGold.toInt() }
        }
        startmagicitems = startingmagicitems.toString()
        return startmagicitems
    }


    fun onEditDndLogSheetButtonPressed(){
        Router.navigateTo(Screen.EditDndLogSheetScreen)
    }
    fun onDeleteDndLogSheetButtonPressed(){
        dndLogSheets.removeAt(dndLogSheetIndex)
        logSheetList.removeAt(logsheetItemIndex)
        Router.navigateTo(Screen.LogSheetsScreen)
    }
    fun onSubmitDndLogSheetButtonPressed(){
        var newDndLogSheet = dndLogSheetBlank
        var newlogsheetitem = logSheetItemBlank
        // build a DndAlLogSheet object and copyToRealm
        // and add to dndLogSheets

        if (logsheetItemIndex==-1){ //new logsheet add to dndLogSheets and logsheets
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
        }else{ // old logsheet edit at logsheetItemIndex
            if (!playerdcinumber.equals("")){
                dndLogSheets[dndLogSheetIndex].playerDCInumber = playerdcinumber.value.toString() }
            if (!charactername.equals("")){
                dndLogSheets[dndLogSheetIndex].characterName = charactername.value.toString() }
            if (!characterrace.equals("")){
                dndLogSheets[dndLogSheetIndex].characterRace = characterrace.value.toString() }
            if (!classes.equals("")){
                dndLogSheets[dndLogSheetIndex].classes = classes.value.toString() }
            if (!faction.equals("")){
                dndLogSheets[dndLogSheetIndex].faction = faction.value.toString() }
        }


        Router.navigateTo(Screen.LogSheetsScreen)
    }

    // use RealmResults to build this list
    var dndLogSheets = mutableListOf<DndAlLogSheet>()
    var dndLogSheetIndex = -1
    fun buildDndLogSheets(){
        dndLogSheets.add(DndAlLogSheet("1","1",GameType().DND,"12345678910",
            "Ben Dover","Human","fighter;rogue;rogue","none",
            "1:1;2:1"))
    }

    // user RealmResults to build this list
    var dndLogSheetEntries = mutableListOf<DndAlEntry>()
    fun buildDndEntries(){
        dndLogSheetEntries = getDndEntries("1")
    }

    fun onDndEntryItemClicked(index: Int){
        dndEntryItemIndex = index
        dndLogSheetEntry = dndLogSheetEntries[index]
        Router.navigateTo(Screen.DndEntryScreen)
    }

    fun getTotalGold(startgold: String, plusminus: String): String{
        val startingGold = startgold.toInt()
        val plusminus = plusminus.toInt()
        val goldtotal = startingGold + plusminus
        return goldtotal.toString()
    }

    fun getTotalDowntime(startdowntime: String, plusminus: String): String{
        val startingDowntime = startdowntime.toInt()
        val plusminus = plusminus.toInt()
        val downtimetotal = startingDowntime + plusminus
        return downtimetotal.toString()
    }

    fun getTotalPermanentMagicItems(startmagicitems: String, plusminus: String): String{
        val startmagicitems = startmagicitems.toInt()
        val plusminus = plusminus.toInt()
        val magicitemstotal = startmagicitems + plusminus
        return magicitemstotal.toString()
    }

    fun onNewDndEntryButtonPressed(){
        dndEntryItemIndex = -1
        Router.navigateTo(Screen.EditDNDentryScreen)
    }
    fun onEditDndEntryButtonPressed(){
        Router.navigateTo(Screen.EditDNDentryScreen)
    }
    fun onDeleteDndEntryButtonPressed(){
        //only allow delete of most recent entry
        Router.navigateTo(Screen.DndLogSheetScreen)
    }
    fun onSubmitDndEntryButtonPressed(){
        //get data from viewModel variables
        //change object in list
        //change RealmObject
        if (dndEntryItemIndex==-1){// make a new entry and add it to the list

        }else{ // edit dndEntries[dndEntryItemIndex]

        }
        Router.navigateTo(Screen.DndLogSheetScreen)
    }


    fun getDndEntries(logsheetID: String): MutableList<DndAlEntry> {
        val dndEntryList = mutableListOf<DndAlEntry>()
        // use RealmResults to build dndLogSheetEntries

        // test data for ui
        for (i in 0 until (logsheetID.toInt()+3)){
            dndEntryList.add(DndAlEntry((i+1).toString(),myProfile.id,logsheetID,"DDAL",
                "Test Adventure "+(i+1).toString(),(i*3+1).toString()+" January 2021","testDM",
                "1",(i*1000).toString(),(i*2).toString(),(i*3).toString(),
                "Y",(i*1000).toString(),(i).toString(),(i+1).toString(),"none",
                (i*1000).toString(),((i+1)*2).toString(),((i+2)*2).toString(),
                "This "+(i+1)+"was a blast!"))
        }
        return dndEntryList
    }

    //mtg log sheet stuff
    //use RealmResults to build this list
    var mtgLogSheetBlank = MTGlogsheet("","",GameType().MTG,
            "","","")
    var mtgLogSheets = mutableListOf<MTGlogsheet>()
    var mtgLogSheetEntries = mutableListOf<MtgEntry>()
    var mtgLogSheetEntryBlank = MtgEntry("","","")
    var mtgLogSheetEntry = mtgLogSheetEntryBlank
    var mtgLogSheet = mtgLogSheetBlank
    var mtgLogSheetIndex = -1
    var mtgEntryItemIndex = -1
    private var _winner = MutableLiveData("")
    var winner: LiveData<String> = _winner
    fun onWinnerChange(winner: String){
        _winner.value = winner
    }
    fun buildMtgLogSheets(){
        mtgLogSheets.add(MTGlogsheet("2",myProfile.id,GameType().MTG,
            players,"2 January 2021",mtgEntries))

    }
    var mtgEntries = ""
    fun onNewMtgLogSheetEntryButtonPressed(){
        mtgEntryItemIndex = -1
        Router.navigateTo(Screen.EditMTGentryScreen)
    }
    fun onEditMtgLogSheetButtonPressed(){
        Router.navigateTo(Screen.EditMtgLogSheetScreen)
    }
    fun onDeleteMtgLogSheetButtonPressed(){
        mtgLogSheets.removeAt(mtgLogSheetIndex)
        logSheetList.removeAt(logsheetItemIndex)
        Router.navigateTo(Screen.LogSheetsScreen)
    }
    fun buildMtgLogSheetEntries(){
        mtgLogSheetEntries.add(MtgEntry("1","1",testListOfPlayers[1]))
        mtgLogSheetEntries.add(MtgEntry("2","1",testListOfPlayers[2]))
        mtgLogSheetEntries.add(MtgEntry("3","1",testListOfPlayers[0]))
        mtgLogSheetEntries.forEach{ entry ->
            mtgEntries = entry.id+":"+entry.logsheetID+";"
        }
    }

    var players = ""
    var magicGameNumber = 1
    var testListOfPlayers = mutableListOf<String>()
    fun buildPlayersList() {
        testListOfPlayers.add("Jon Donnson")
        testListOfPlayers.add("Judy Patutti")
        testListOfPlayers.add("Claude Bawls")
        testListOfPlayers.add("I.P. Freehly")
        testListOfPlayers.forEach { player ->
            players += player+";"
        }
    }
    fun onMtgEntryItemClicked(index: Int,count: Int){
        mtgEntryItemIndex = index
        magicGameNumber = count
        mtgLogSheetEntry = mtgLogSheetEntries[index]
        Router.navigateTo(Screen.MtgEntryScreen)
    }

    fun onEditMtgEntryButtonPressed(){
        Router.navigateTo(Screen.EditMTGentryScreen)
    }

    fun onDeleteMtgEntryButtonPressed(){
        //delete from Realm and local list
        mtgLogSheetEntries.removeAt(mtgEntryItemIndex)
        Router.navigateTo(Screen.MtgLogSheetScreen)
    }

    fun onSubmitMtgEntryButtonPressed(){
        //update Realm and mtgLogSheetEntries
        mtgLogSheetEntries[mtgEntryItemIndex].winner = winner.toString()
        Router.navigateTo(Screen.MtgLogSheetScreen)
    }
/*
    private    fun buildMonopolyLogSheetsList(){
        testListOfMonopolyLogSheets.add(
            MonopolyLogSheet("1","1",
                getTestPlayerList(),getMonopolyEntries("1",getTestPlayerList())))
        testListOfMonopolyLogSheets.add(
            MonopolyLogSheet("2","1",
                getTestPlayerList(),getMonopolyEntries("2",getTestPlayerList())))
        testListOfMonopolyLogSheets.add(
            MonopolyLogSheet("3","1",
                getTestPlayerList(),getMonopolyEntries("3",getTestPlayerList())))
    }
*/
    private fun getMonopolyEntries(logsheetID: String,testPlayerList:List<String>): MutableList<MonopolyEntry> {
        var monopEntryList = mutableListOf<MonopolyEntry>()

        for (i in 1 until testPlayerList.size){
            var winner = testPlayerList[i]
            monopEntryList.add(MonopolyEntry(i.toString(),logsheetID,winner))
        }

        return monopEntryList
    }
}