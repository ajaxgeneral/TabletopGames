package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TimeBlock")
data class TimeBlock(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "duration")
    var duration: Int = 12,
    @ColumnInfo(name = "dayMonthYear")
    var dayMonthYear: String,
    @ColumnInfo(name = "gameTable")
    var gameTable: Int,
    @ColumnInfo(name = "seat")
    var seat: Int,
    @ColumnInfo(name = "reservationList")
    var reservationList: String // list of reservationIDs for this timeBlock
)
