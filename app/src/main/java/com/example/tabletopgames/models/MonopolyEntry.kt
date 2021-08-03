package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MonopolyEntry")
data class MonopolyEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "logsheetID")
    var logsheetID: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "dayMonthYear")
    var dayMonthYear: String,
    @ColumnInfo(name = "winner")
    var winner: String
)
