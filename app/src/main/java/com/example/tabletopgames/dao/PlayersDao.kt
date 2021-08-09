package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.Players

@Dao
interface PlayersDao {
    @Query("select * from Players where profileID = :profileID and logsheetID = :logsheetID")
    fun getPlayersForThis(profileID: Int,logsheetID: Int) : Players?
    @Insert
    suspend fun insert(players: Players) : Long
    @Update
    suspend fun update(players: Players)
    @Delete
    suspend fun delete(players: Players)
}