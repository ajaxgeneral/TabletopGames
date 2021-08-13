package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.Winners

@Dao
interface WinnersDao {
    @Query("select * from Winners where profileID = :profileID and logsheetID = :logsheetID and entryID = :entryID")
    suspend fun getWinnersFor(profileID:Int,logsheetID:Int,entryID:Int) : Winners?
    @Insert
    suspend fun insert(winners: Winners)
    @Update
    suspend fun update(winners: Winners)
    @Delete
    suspend fun delete(winners: Winners)
}