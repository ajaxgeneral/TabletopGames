package com.example.tabletopgames.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopgames.models.ClassesANDLevels
import com.example.tabletopgames.models.LogSheet
import kotlinx.coroutines.flow.Flow

@Dao
interface LogSheetDao {
    @Query("select * from LogSheet where profileID = :profileID")
    fun getAllLogSheetsFor(profileID: Int) : List<LogSheet>?
    @Insert
    suspend fun insert(logSheet: LogSheet)
    @Update
    suspend fun update(logSheet: LogSheet)
    @Delete
    suspend fun delete(logSheet: LogSheet)
}