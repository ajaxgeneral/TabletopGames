package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.DndAlEntry
import com.example.tabletopgames.models.MonopolyEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MonopolyEntryDao {
    @Query("select * from MonopolyEntry where profileID = :profileID and logsheetID = :logsheetID")
    suspend fun getAllMonopolyEntriesFor(profileID:Int,logsheetID:Int) : List<MonopolyEntry>?
    @Insert
    suspend fun insert(monopolyEntry: MonopolyEntry)
    @Update
    suspend fun update(monopolyEntry: MonopolyEntry)
    @Delete
    suspend fun delete(monopolyEntry: MonopolyEntry)
}