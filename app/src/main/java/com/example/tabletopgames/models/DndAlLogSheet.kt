package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DndAlLogSheet")
data class DndAlLogSheet(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "gameType")
    var gameType: String,
    @ColumnInfo(name = "playerDCInumber")
    var playerDCInumber: String,
    @ColumnInfo(name = "characterName")
    var characterName: String,
    @ColumnInfo(name = "characterRace")
    var characterRace: String,
    @ColumnInfo(name = "classes")
    var classes: String,
    @ColumnInfo(name = "faction")
    var faction: String
)
