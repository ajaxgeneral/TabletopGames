package com.example.tabletopgames.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tabletopgames.dao.*
import com.example.tabletopgames.models.*

@Database(entities = [ClassesANDLevels::class,DndAlEntry::class,DndAlLogSheet::class,
                     LogSheet::class,MonopolyEntry::class,MonopolyLogSheet::class,
                     MtgEntry::class,MTGlogsheet::class,MyLogin::class,MyProfile::class,
                     Players::class,Reservation::class,Winners::class], version = 1)
abstract class TabletopGamesDatabase : RoomDatabase() {
    abstract fun classesANDlevelDao(): ClassesANDLevelsDao
    abstract fun dndAlEntryDao(): DndAlEntryDao
    abstract fun dndAlLogSheetDao(): DndAlLogSheetDao
    abstract fun logSheetDao(): LogSheetDao
    abstract fun monopolyEntryDao(): MonopolyEntryDao
    abstract fun monopolyLogSheetDao(): MonopolyLogSheetDao
    abstract fun mtgEntryDao(): MtgEntryDao
    abstract fun mtgLogSheetDao(): MTGlogsheetDao
    abstract fun myLoginDao(): MyLoginDao
    abstract fun myProfileDao(): MyProfileDao
    abstract fun playersDao(): PlayersDao
    abstract fun reservationDao(): ReservationDao
    abstract fun winnersDao(): WinnersDao



    companion object {
        @Volatile
        private var INSTANCE: TabletopGamesDatabase? = null
        fun getInstance(context: Context): TabletopGamesDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                   instance = Room.databaseBuilder(
                       context.applicationContext,
                       TabletopGamesDatabase::class.java,
                       "TabletopGamesDatabase"
                   ).fallbackToDestructiveMigration()
                       .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }

}