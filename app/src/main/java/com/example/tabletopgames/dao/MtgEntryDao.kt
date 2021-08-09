package com.example.tabletopgames.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopgames.models.MonopolyEntry
import com.example.tabletopgames.models.MtgEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MtgEntryDao {
    @Query("select * from MtgEntry where profileID = :profileID and logsheetID = :logsheetID")
    fun getAllMtgEntriesFor(profileID:Int,logsheetID:Int) : List<MtgEntry>?

    @Insert
    suspend fun insert(mtgEntry: MtgEntry): Long
    @Update
    suspend fun update(mtgEntry: MtgEntry)
    @Delete
    suspend fun delete(mtgEntry: MtgEntry)
}