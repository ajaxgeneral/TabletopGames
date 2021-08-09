package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.ClassesANDLevels
import com.example.tabletopgames.models.DndAlLogSheet

@Dao
interface DndAlLogSheetDao {
    @Query("select * from DndAlLogSheet where profileID = :profileID and id = :logsheetID")
    fun getThisDndLogSheet(profileID: Int,logsheetID: Int) : DndAlLogSheet?
    @Query("select * from DndAlLogSheet where profileID = :profileID")
    fun getAllDndLogSheetsFor(profileID: Int) : List<DndAlLogSheet>?
    @Insert
    suspend fun insert(dndAlLogSheet: DndAlLogSheet)
    @Update
    suspend fun update(dndAlLogSheet: DndAlLogSheet)
    @Delete
    suspend fun delete(dndAlLogSheet: DndAlLogSheet)
}