package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.TimeBlock

@Dao
interface TimeBlockDao {
    @Query("select * from TimeBlock where gameTable = :gameTable and seat = :seat and dayMonthYear = :dayMonthYear")
    suspend fun getThisSeatsTimeBlock(gameTable: Int,seat: Int,dayMonthYear: String) : List<TimeBlock>?
    @Insert
    suspend fun insert(timeBlock: TimeBlock)
    @Update
    suspend fun update(timeBlock: TimeBlock)
    @Delete
    suspend fun delete(timeBlock: TimeBlock)

}