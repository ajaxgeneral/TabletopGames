package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DndAlEntry")
open class DndAlEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "profileID")
    var profileID: Int,
    @ColumnInfo(name = "logsheetID")
    var logsheetID: Int,
    @ColumnInfo(name = "adventureCode")
    var adventureCode: String,
    @ColumnInfo(name = "adventureName")
    var adventureName: String,
    @ColumnInfo(name = "dayMonthYear")
    var dayMonthYear: String,
    @ColumnInfo(name = "dmDCInumber")
    var dmDCInumber: String,
    @ColumnInfo(name = "startingLevel")
    var startingLevel: String,
    @ColumnInfo(name = "startingGold")
    var startingGold: String,
    @ColumnInfo(name = "startingDowntime")
    var startingDowntime: String,
    @ColumnInfo(name = "startingPermanentMagicItems")
    var startingPermanentMagicItems: String,
    @ColumnInfo(name = "levelAccepted")
    var levelAccepted: String,
    @ColumnInfo(name = "goldPlusMinus")
    var goldPlusMinus: String,
    @ColumnInfo(name = "downtimePlusMinus")
    var downtimePlusMinus: String,
    @ColumnInfo(name = "permanentMagicItemsPlusMinus")
    var permanentMagicItemsPlusMinus: String,
    @ColumnInfo(name = "newClassLevel")
    var newClassLevel: String,
    @ColumnInfo(name = "newGoldTotal")
    var newGoldTotal: String,
    @ColumnInfo(name = "newDowntimeTotal")
    var newDowntimeTotal: String,
    @ColumnInfo(name = "newPermanentMagicItemTotal")
    var newPermanentMagicItemTotal: String,
    @ColumnInfo(name = "adventureNotes")
    var adventureNotes: String
)