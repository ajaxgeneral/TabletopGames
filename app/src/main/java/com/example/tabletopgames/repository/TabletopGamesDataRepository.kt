package com.example.tabletopgames.repository


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
    suspend fun emailExists(email:String): List<MyLogin>? {
        return myLoginDao.emailExists(email)
    }

    suspend fun findMyLogin(myLogin: MyLogin): MyLogin? {
        return myLoginDao.findMyLogin(myLogin.email,myLogin.password)
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
    suspend fun getMyProfileByID(id: Int): MyProfile? {
        return myProfileDao.getMyProfileByID(id)
    }
    suspend fun getMyProfile(email: String): MyProfile? {
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
    suspend fun getLogSheetsFor(profileID: Int): List<LogSheet>? {
        return logSheetDao.getAllLogSheetsFor(profileID)
    }
    suspend fun addNewLogSheet(logsheet:LogSheet): Long {
        return logSheetDao.insert(logsheet)
    }
    suspend fun updateThisLogSheet(logsheet: LogSheet){
        logSheetDao.update(logsheet)
    }
    suspend fun deleteThisLogSheet(logsheet: LogSheet){
        logSheetDao.delete(logsheet)
    }
    suspend fun deleteAllLogSheetsFor(profileID: Int){
        logSheetDao.deleteAllLogSheetsFor(profileID)
    }
    // dnd logsheet functions
    suspend fun getThisDndLogSheet(profileID: Int,logsheetID: Int): DndAlLogSheet? {
        return dndAlLogSheetDao.getThisDndLogSheet(profileID,logsheetID)
    }
    suspend fun getAllDndLogSheetsFor(profileID: Int): List<DndAlLogSheet>? {
        val logsheetList = dndAlLogSheetDao.getAllDndLogSheetsFor(profileID)
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
    suspend fun getAllDndEntriesForThisPC(profileID: Int, logsheetID: Int): List<DndAlEntry>? {
        return dndAlEntryDao.getDndEntriesFor(profileID,logsheetID)
    }

    suspend fun deleteAllDndEntriesFor(profileID: Int, logsheetID: Int){
        dndAlEntryDao.deleteAllDndEntriesFor(profileID,logsheetID)
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
    suspend fun getThisMtgLogSheet(profileID: Int,logsheetID: Int): MTGlogsheet? {
        return mtgLogSheetDao.getThisMtgLogSheet(profileID,logsheetID)
    }
    suspend fun getAllMtgLogSheetsFor(profileID: Int): List<MTGlogsheet>? {
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
        var lastEntry = mtgEntries?.get(0)
        var thisEntry = mtgEntries?.get(0)
        mtgEntries?.forEach{ entry ->
            if (entry.dayMonthYear> thisEntry!!.dayMonthYear ){
                lastEntry = entry
                thisEntry = entry
            }
        }
        return lastEntry
    }
    suspend fun addNewMtgEntry(mtgEntry: MtgEntry): Long {
       return mtgEntryDao.insert(mtgEntry)
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
    suspend fun getPlayerObjectForThisGame(profileID: Int, logsheetID: Int): Players? {
        return playersDao.getPlayersForThis(profileID,logsheetID)
    }
    suspend fun addNewPlayersForANewGame(players: Players): Long{
        return playersDao.insert(players)
    }
    suspend fun updatePlayersForThisGame(players: Players){
        playersDao.update(players)
    }
    suspend fun deleteThisGamesPlayers(players: Players){
        playersDao.delete(players)
    }
    // reservation functions
    suspend fun thisDaysReservationsFor(dayMonthYear: String,gameTable: String,seat: String) : List<Reservation>?{
        return reservationDao.thisDaysReservationsFor(dayMonthYear,gameTable,seat)
    }

    suspend fun getReservationsFor(profileID: Int): List<Reservation>? {
        return reservationDao.getReservationsFor(profileID)
    }
    suspend fun getThisReservation(id: Int): Reservation? {
       return reservationDao.getOneReservationByID(id)
    }
    suspend fun getReservationsByGameType(gameType: String): List<Reservation>? {
        return reservationDao.getReservationsByGameType(gameType)
    }
    suspend fun getReservationsByDayMonthYear(dayMonthYear: String): List<Reservation>? {
        return reservationDao.getReservationsByDayMonthYear(dayMonthYear)
    }
    suspend fun getReservationsByTime(time: String): List<Reservation>? {
        return reservationDao.getReservationsByTime(time)
    }
    suspend fun getReservationsBySeat(seat: String){
        reservationDao.getReservationsBySeat(seat)
    }
    suspend fun getReservationsByDuration(duration: String){
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