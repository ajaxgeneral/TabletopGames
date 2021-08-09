package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Reservation")
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "gameType")
    var gameType: String,
    @ColumnInfo(name = "dayMonthYear")
    var dayMonthYear: String,
    @ColumnInfo(name = "time")
    var time: String,
    @ColumnInfo(name = "table")
    var gameTable: String,
    @ColumnInfo(name = "seat")
    var seat: String,
    @ColumnInfo(name = "duration")
    var duration: String
)
