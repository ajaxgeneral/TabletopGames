package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Winners")
data class Winners (
    @PrimaryKey(autoGenerate = true)
   var id: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "logsheetID")
    var logsheetID: Int,
    @ColumnInfo(name = "entryID")
    var entryID: Int,
    @ColumnInfo(name = "members")
    var members: String  // ';' separated list of names
)