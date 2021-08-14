package com.example.tabletopgames.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopgames.models.ClassesANDLevels
import com.example.tabletopgames.models.DndAlEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DndAlEntryDao {
    @Query("select * from DndAlEntry where profileID = :profileID and logsheetID = :logsheetID ")
    suspend fun getDndEntriesFor(profileID: Int, logsheetID: Int) : List<DndAlEntry>?
    @Insert
    suspend fun insert(dndAlEntry: DndAlEntry) : Long
    @Update
    suspend fun update(dndAlEntry: DndAlEntry)
    @Delete
    suspend fun delete(dndAlEntry: DndAlEntry)
    @Query("delete from DndAlEntry where profileID = :profileID and logsheetID = :logsheetID")
    suspend fun deleteAllDndEntriesFor(profileID: Int, logsheetID: Int)
}