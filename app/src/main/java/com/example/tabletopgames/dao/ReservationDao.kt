package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.Reservation

@Dao
interface ReservationDao {

    @Query("select * from Reservation where profileID = :profileID")
    fun getReservationsFor(profileID: Int) : List<Reservation>?
    @Query("select * from Reservation where id = :id")
    fun getOneReservationByID(id: Int) : Reservation?
    @Query("select * from Reservation where gameType = :gameType")
    fun getReservationsByGameType(gameType: String) : List<Reservation>?
    @Query("select * from Reservation where dayMonthYear = :dayMonthYear")
    fun getReservationsByDayMonthYear(dayMonthYear: String) : List<Reservation>?
    @Query("select * from Reservation where time = :time")
    fun getReservationsByTime(time: String) : List<Reservation>?
    @Query("select * from Reservation where seat = :seat")
    fun getReservationsBySeat(seat:String) : List<Reservation>?
    @Query("select * from Reservation where duration = :duration")
    fun getReservationsByDuration(duration:String) : List<Reservation>?


    @Insert
    suspend fun insert(reservation: Reservation)
    @Update
    suspend fun update(reservation: Reservation)
    @Delete
    suspend fun delete(reservation: Reservation)
}