package com.example.tabletopgames.viewModels

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.tabletopgames.dao.*
import com.example.tabletopgames.database.TabletopGamesDatabase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import java.io.IOException

class MainViewModelTest {
    private lateinit var myLoginDao: MyLoginDao
    private lateinit var myProfileDao: MyProfileDao
    private lateinit var dndAlEntryDao: DndAlEntryDao
    private lateinit var logSheetDao: LogSheetDao
    private lateinit var mtgEntryDao: MtgEntryDao
    private lateinit var mtgLogSheetDao: MTGlogsheetDao
    private lateinit var playersDao: PlayersDao
    private lateinit var reservationDao: ReservationDao
    private lateinit var winnersDao: WinnersDao
    private lateinit var db: TabletopGamesDatabase

    @Before
    fun createDb(){
        val context =
            InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context,
            TabletopGamesDatabase::class.java).allowMainThreadQueries().build()
        myLoginDao = db.myLoginDao()
        myProfileDao = db.myProfileDao()
        dndAlEntryDao = db.dndAlEntryDao()
        logSheetDao = db.logSheetDao()
        mtgEntryDao = db.mtgEntryDao()
        mtgLogSheetDao = db.mtgLogSheetDao()
        playersDao = db.playersDao()
        reservationDao = db.reservationDao()
        winnersDao = db.winnersDao()
    }
    @After
    @Throws(IOException::class)
    fun deleteDb(){
        db.close()
    }

    @Test
    fun getImg() {
    }

    @Test
    fun backButton() {
    }

    @Test
    fun onProfilePressed() {
    }

    @Test
    fun onReservationsPressed() {
    }

    @Test
    fun onLogSheetsPressed() {
    }

    @Test
    fun onHomeButtonPressed() {
    }

    @Test
    fun onEmailChange() {
    }

    @Test
    fun onPasswordChange() {
    }

    @Test
    fun onLogoutPressed() {
    }

    @Test
    fun onLoginPressed() {
    }

    @Test
    fun isValidLogin() {
    }

    @Test
    fun onPhoneChange() {
    }

    @Test
    fun onFirstNameChange() {
    }

    @Test
    fun onLastNameChange() {
    }

    @Test
    fun onDeleteProfilePressed() {
    }

    @Test
    fun onEditProfilePressed() {
    }

    @Test
    fun onCreateProfilePressed() {
    }

    @Test
    fun onSubmitProfilePressed() {
    }

    @Test
    fun onGameTypeChange() {
    }

    @Test
    fun onDayChange() {
    }

    @Test
    fun onMonthChange() {
    }

    @Test
    fun onTimeChange() {
    }

    @Test
    fun onTableChange() {
    }

    @Test
    fun onSeatChange() {
    }

    @Test
    fun onDurationChange() {
    }

    @Test
    fun onSubmitReservationButtonPressed() {
    }

    @Test
    fun onEditReservationButtonPressed() {
    }

    @Test
    fun onNewReservationButtonPressed() {
    }

    @Test
    fun onDeleteReservationButtonPressed() {
    }

    @Test
    fun onReservationListItemClicked() {
    }

    @Test
    fun onLogSheetListItemClicked() {
    }

    @Test
    fun onPlayerDCInumberChanged() {
    }

    @Test
    fun onCharacterNameChange() {
    }

    @Test
    fun onCharacterRaceChange() {
    }

    @Test
    fun onClassChange() {
    }

    @Test
    fun onFactionChange() {
    }

    @Test
    fun onNewDndLogSheetButtonPressed() {
    }

    @Test
    fun onNewMtgLogSheetButtonPressed() {
    }

    @Test
    fun onAdvNameChange() {
    }

    @Test
    fun onAdvCodeChange() {
    }

    @Test
    fun onDayMonthYearChange() {
    }

    @Test
    fun onDmDciNumberChange() {
    }

    @Test
    fun onGoldPlusMinusChange() {
    }

    @Test
    fun onDowntimePlusMinusChange() {
    }

    @Test
    fun onPermanentMagicItemsPlusMinusChange() {
    }

    @Test
    fun onNewClassChange() {
    }

    @Test
    fun onAdvNotesChange() {
    }

    @Test
    fun getStartingMagicItems() {
    }

    @Test
    fun onEditDndLogSheetButtonPressed() {
    }

    @Test
    fun onDeleteDndLogSheetButtonPressed() {
    }

    @Test
    fun onSubmitDndLogSheetButtonPressed() {
    }

    @Test
    fun onDndEntryItemClicked() {
    }

    @Test
    fun onNewDndEntryButtonPressed() {
    }

    @Test
    fun onEditDndEntryButtonPressed() {
    }

    @Test
    fun onDeleteDndEntryButtonPressed() {
    }

    @Test
    fun onSubmitDndEntryButtonPressed() {
    }

    @Test
    fun onPlayersChange() {
    }

    @Test
    fun onWinnerChange() {
    }

    @Test
    fun onNewMtgLogSheetEntryButtonPressed() {
    }

    @Test
    fun onEditMtgLogSheetButtonPressed() {
    }

    @Test
    fun onDeleteMtgLogSheetButtonPressed() {
    }

    @Test
    fun onAddPlayerButtonPressed() {
    }

    @Test
    fun onSubmitMtgLogSheetButtonPressed() {
    }

    @Test
    fun onMtgEntryItemClicked() {
    }

    @Test
    fun onEditMtgEntryButtonPressed() {
    }

    @Test
    fun onDeleteMtgEntryButtonPressed() {
    }

    @Test
    fun onSubmitMtgEntryButtonPressed() {
    }
}