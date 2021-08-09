package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LogSheet")
data class LogSheet(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "gameType")
    var gameType: String,
    @ColumnInfo(name = "dateCreated")
    var dateCreated: String
)