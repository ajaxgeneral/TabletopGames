package com.example.tabletopgames

import android.app.Application
import com.example.tabletopgames.database.TabletopGamesDatabase
import com.example.tabletopgames.repository.TabletopGamesDataRepository


class MyApplication :Application() {
    private val database by lazy {
        TabletopGamesDatabase.getInstance(this)
    }
    val repository by lazy {
        database.let {
            TabletopGamesDataRepository(
                it.classesANDlevelDao(),it.dndAlEntryDao(),it.dndAlLogSheetDao(),
                it.logSheetDao(),it.monopolyEntryDao(),it.monopolyLogSheetDao(),
                it.mtgEntryDao(),it.mtgLogSheetDao(),it.myLoginDao(),it.myProfileDao(),
                it.playersDao(),it.reservationDao(),it.winnersDao()
            )
        }
    }
}

