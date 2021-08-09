package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MTGlogsheet")
data class MTGlogsheet(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "gameType")
    var gameType: String,
    @ColumnInfo(name = "players")
    var playersID: Int,
    @ColumnInfo(name = "dayMonthYear")
    var dayMonthYear: String
)
