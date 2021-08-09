package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MonopolyLogSheet")
data class MonopolyLogSheet(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "logsheetID")
    var logsheetID: Int,
    @ColumnInfo(name = "gameType")
    var gameType: String,
    @ColumnInfo(name = "players")
    var players: String,
    @ColumnInfo(name = "dayMonthYear")
    var dayMonthYear: String,
    @ColumnInfo(name = "logEntries")
    var logEntries: String
)
