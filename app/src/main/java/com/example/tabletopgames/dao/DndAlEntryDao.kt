package com.example.tabletopgames.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopgames.models.ClassesANDLevels
import com.example.tabletopgames.models.DndAlEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DndAlEntryDao {
    @Query("select * from DndAlEntry where profileID = :profileID and logsheetID = :logsheetID")
    fun getAllDndEntriesFor(profileID: Int,logsheetID: Int) : List<DndAlEntry>?
    @Query("select * from DndAlEntry where profileID = :profileID and logsheetID = :logsheetID ")
    fun getDndEntriesFor(profileID: Int, logsheetID: Int) : List<DndAlEntry>
    @Insert
    suspend fun insert(dndAlEntry: DndAlEntry)
    @Update
    suspend fun update(dndAlEntry: DndAlEntry)
    @Delete
    suspend fun delete(dndAlEntry: DndAlEntry)
}