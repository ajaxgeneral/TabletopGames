package com.example.tabletopgames.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopgames.models.MTGlogsheet
import com.example.tabletopgames.models.MonopolyLogSheet
import kotlinx.coroutines.flow.Flow

@Dao
interface MTGlogsheetDao {
    @Query("select * from MTGlogsheet where profileID = :profileID and id = :logsheetID")
    fun getThisMtgLogSheet(profileID:Int,logsheetID:Int) : MTGlogsheet?
    @Query("select * from MTGlogsheet where profileID = :profileID")
    fun getAllMtgLogSheetsFor(profileID: Int) : List<MTGlogsheet>?
    @Insert
    suspend fun insert(mtgLogSheet: MTGlogsheet)
    @Update
    suspend fun update(mtgLogSheet: MTGlogsheet)
    @Delete
    suspend fun delete(mtgLogSheet: MTGlogsheet)
}