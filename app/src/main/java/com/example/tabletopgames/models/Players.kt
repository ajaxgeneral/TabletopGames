package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Players")
data class Players (
    @PrimaryKey(autoGenerate = true)
   var id: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "logsheetID")
    var logsheetID: Int,
    @ColumnInfo(name = "members")
    var members: String // when created is a ; separated list
)