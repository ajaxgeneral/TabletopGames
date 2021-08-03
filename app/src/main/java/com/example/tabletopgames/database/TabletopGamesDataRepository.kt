package com.example.tabletopgames.database


import com.example.tabletopgames.dao.*
import com.example.tabletopgames.models.*

class TabletopGamesDataRepository(
    private val classesANDLevelsDao: ClassesANDLevelsDao,
    private val dndAlEntryDao: DndAlEntryDao,
    private val dndAlLogSheetDao: DndAlLogSheetDao,
    private val logSheetDao: LogSheetDao,
    private val monopolyEntryDao: MonopolyEntryDao,
    private val monopolyLogSheetDao: MonopolyLogSheetDao,
    private val mtgEntryDao: MtgEntryDao,
    private val mtgLogSheetDao: MTGlogsheetDao,
    private val myLoginDao: MyLoginDao,
    private val myProfileDao: MyProfileDao,
    private val playersDao: PlayersDao,
    private val reservationDao: ReservationDao,
    private val winnersDao: WinnersDao
) {

    //  login functions
    fun emailExists(email:String): Boolean{
        var doesExist = false
        val result = myLoginDao.emailExists(email)
        if (result!=null){ doesExist = true }
        return doesExist
    }

    fun findMyLogin(myLogin: MyLogin): Boolean {
       var foundLogin = myLoginDao.findMyLogin(myLogin.email,myLogin.password)
        var found = false
        if (foundLogin!=null){ found = true }
        return found
    }
    suspend fun addNewLogin(myLogin: MyLogin){
        myLoginDao.insert(myLogin)
    }
    suspend fun updateThisLogin(myLogin: MyLogin){
        myLoginDao.update(myLogin)
    }
    suspend fun deleteThisLogin(myLogin: MyLogin){
        myLoginDao.delete(myLogin)
    }
    // profile functions
    fun getMyProfileByID(id: Int): MyProfile? {
        return myProfileDao.getMyProfileByID(id)
    }
    fun getMyProfile(email: String): MyProfile? {
        return myProfileDao.getMyProfile(email)
    }
    suspend fun addNewProfile(myProfile: MyProfile){
        myProfileDao.insert(myProfile)
    }
    suspend fun updateThisProfile(myProfile: MyProfile){
        myProfileDao.update(myProfile)
    }
    suspend fun deleteThisProfile(myProfile: MyProfile){
        myProfileDao.delete(myProfile)
    }
    // logsheet functions
    fun getLogSheetsFor(profileID: Int): List<LogSheet>? {
        return logSheetDao.getAllLogSheetsFor(profileID)
    }
    suspend fun addNewLogSheet(logsheet:LogSheet){
        logSheetDao.insert(logsheet)
    }
    suspend fun updateThisLogSheet(logsheet: LogSheet){
        logSheetDao.update(logsheet)
    }
    suspend fun deleteThisLogSheet(logsheet: LogSheet){
        logSheetDao.delete(logsheet)
    }
    // dnd logsheet functions
    fun getThisDndLogSheet(profileID: Int,logsheetID: Int): DndAlLogSheet? {
        return dndAlLogSheetDao.getThisDndLogSheet(profileID,logsheetID)
    }
    fun getAllDndLogSheetsFor(profileID: Int): List<DndAlLogSheet>? {
        return dndAlLogSheetDao.getAllDndLogSheetsFor(profileID)
    }
    suspend fun addNewDndLogSheet(dndAlLogSheet: DndAlLogSheet){
        dndAlLogSheetDao.insert(dndAlLogSheet)
    }
    suspend fun updateThisDndLogSheet(dndAlLogSheet: DndAlLogSheet){
        dndAlLogSheetDao.update(dndAlLogSheet)
    }
    suspend fun deleteThisDndLogSheet(dndAlLogSheet: DndAlLogSheet){
        dndAlLogSheetDao.delete(dndAlLogSheet)
    }
    // dnd logsheet entry functions
    fun getAllDndEntriesFor(profileID: Int,logsheetID: Int): List<DndAlEntry>? {
        return dndAlEntryDao.getAllDndEntriesFor(profileID,logsheetID)
    }

    suspend fun addNewDndEntry(dndAlEntry: DndAlEntry){
        dndAlEntryDao.insert(dndAlEntry)
    }
    suspend fun updateThisDndEntry(dndAlEntry: DndAlEntry){
        dndAlEntryDao.update(dndAlEntry)
    }
    suspend fun deleteThisDndEntry(dndAlEntry: DndAlEntry){
        dndAlEntryDao.delete(dndAlEntry)
    }
    // mtg logsheet functions
    fun getThisMtgLogSheet(profileID: Int,logsheetID: Int): MTGlogsheet? {
        return mtgLogSheetDao.getThisMtgLogSheet(profileID,logsheetID)
    }
    fun getAllMtgLogSheetsFor(profileID: Int): List<MTGlogsheet>? {
        return mtgLogSheetDao.getAllMtgLogSheetsFor(profileID)
    }

    suspend fun addNewMtgLogSheet(mtgLogSheet: MTGlogsheet){
        mtgLogSheetDao.insert(mtgLogSheet)
    }
    suspend fun updateThisMtgLogSheet(mtgLogSheet: MTGlogsheet){
        mtgLogSheetDao.update(mtgLogSheet)
    }
    suspend fun deleteThisMtgLogSheet(mtgLogSheet: MTGlogsheet){
        mtgLogSheetDao.delete(mtgLogSheet)
    }
    // mtg logsheet entry functions
    suspend fun getAllMtgEntriesFor(profileID: Int,logsheetID: Int): List<MtgEntry>? {
        return mtgEntryDao.getAllMtgEntriesFor(profileID,logsheetID)
    }
    suspend fun getLastMtgEntryFor(profileID: Int,logsheetID: Int): MtgEntry? {
        val mtgEntries = mtgEntryDao.getAllMtgEntriesFor(profileID,logsheetID)
        var lastEntry = mtgEntries[0]
        var thisEntry = mtgEntries[0]
        mtgEntries.forEach{ entry ->
            if (entry.dayMonthYear>thisEntry.dayMonthYear){
                lastEntry = entry
                thisEntry = entry
            }
        }
        return lastEntry
    }
    suspend fun addNewMtgEntry(mtgEntry: MtgEntry){
        mtgEntryDao.insert(mtgEntry)
    }
    suspend fun updateThisMtgEntry(mtgEntry: MtgEntry){
        mtgEntryDao.update(mtgEntry)
    }
    suspend fun deleteThisMtgEntry(mtgEntry: MtgEntry){
        mtgEntryDao.delete(mtgEntry)
    }
    // monopoly logsheet functions
            /*
            *       this feature has been pushed to a future version
            *
            * */
    // players functions
    fun getPlayersForThisGame(profileID: Int,logsheetID: Int): Players? {
        return playersDao.getPlayersForThis(profileID,logsheetID)
    }
    suspend fun addNewPlayersForANewGame(players: Players){
        playersDao.insert(players)
    }
    suspend fun updateThisGamesPlayers(players: Players){
        playersDao.update(players)
    }
    suspend fun deleteThisGamesPlayers(players: Players){
        playersDao.delete(players)
    }
    // reservation functions
    suspend fun getReservationsFor(profileID: Int): List<Reservation>? {
        return reservationDao.getReservationsFor(profileID)
    }
    fun getThisReservation(id: Int): Reservation? {
       return reservationDao.getOneReservationByID(id)
    }
    fun getReservationsByGameType(gameType: String): List<Reservation>? {
        return reservationDao.getReservationsByGameType(gameType)
    }
    fun getReservationsByDayMonthYear(dayMonthYear: String): List<Reservation>? {
        return reservationDao.getReservationsByDayMonthYear(dayMonthYear)
    }
    fun getReservationsByTime(time: String): List<Reservation>? {
        return reservationDao.getReservationsByTime(time)
    }
    fun getReservationsBySeat(seat: String){
        reservationDao.getReservationsBySeat(seat)
    }
    fun getReservationsByDuration(duration: String){
        reservationDao.getReservationsByDuration(duration)
    }
    suspend fun addNewReservation(reservation: Reservation){
        reservationDao.insert(reservation)
    }
    suspend fun updateThisReservation(reservation: Reservation){
        reservationDao.update(reservation)
    }
    suspend fun deleteThisReservation(reservation: Reservation){
        reservationDao.delete(reservation)
    }
    // winners functions
    suspend fun getWinnersForThisGame(profileID: Int,logsheetID: Int,entryID: Int): Winners? {
        return winnersDao.getWinnersFor(profileID,logsheetID,entryID)
    }
    suspend fun addNewWinnersForThisGame(winners: Winners){
        winnersDao.insert(winners)
    }
    suspend fun updateThisGamesWinners(winners: Winners){
        winnersDao.update(winners)
    }
    suspend fun deleteThisGamesWinners(winners: Winners){
        winnersDao.delete(winners)
    }
}