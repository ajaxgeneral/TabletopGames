package com.example.tabletopgames.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopgames.models.MonopolyLogSheet
import kotlinx.coroutines.flow.Flow

@Dao
interface MonopolyLogSheetDao {
    @Query("select * from MonopolyLogSheet where profileID = :profileID and logsheetID = :logsheetID")
    fun getThisMonopolyLogSheet(profileID:Int,logsheetID:Int) : MonopolyLogSheet?
    @Query("select * from MonopolyLogSheet where profileID = :profileID")
    fun getAllMonopolyLogSheetsFor(profileID: Int) : List<MonopolyLogSheet>?
    @Insert
    suspend fun insert(monopolyLogSheet: MonopolyLogSheet)
    @Update
    suspend fun update(monopolyLogSheet: MonopolyLogSheet)
    @Delete
    suspend fun delete(monopolyLogSheet: MonopolyLogSheet)
}