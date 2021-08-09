package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.Reservation

@Dao
interface ReservationDao {
    @Query("select * from Reservation where `table` = :gameTable and seat = :seat and dayMonthYear = :dayMonthYear")
    suspend fun thisDaysReservationsFor(dayMonthYear: String,gameTable: String,seat: String): List<Reservation>?
    @Query("select * from Reservation where profileID = :profileID")
    suspend fun getReservationsFor(profileID: Int) : List<Reservation>?
    @Query("select * from Reservation where id = :id")
    suspend fun getOneReservationByID(id: Int) : Reservation?
    @Query("select * from Reservation where gameType = :gameType")
    suspend fun getReservationsByGameType(gameType: String) : List<Reservation>?
    @Query("select * from Reservation where dayMonthYear = :dayMonthYear")
    suspend fun getReservationsByDayMonthYear(dayMonthYear: String) : List<Reservation>?
    @Query("select * from Reservation where time = :time")
    suspend fun getReservationsByTime(time: String) : List<Reservation>?
    @Query("select * from Reservation where seat = :seat")
    suspend fun getReservationsBySeat(seat:String) : List<Reservation>?
    @Query("select * from Reservation where duration = :duration")
    suspend fun getReservationsByDuration(duration:String) : List<Reservation>?


    @Insert
    suspend fun insert(reservation: Reservation)
    @Update
    suspend fun update(reservation: Reservation)
    @Delete
    suspend fun delete(reservation: Reservation)
}