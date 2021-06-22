package com.example.tabletopgames.models

data class DndAlEntry(
    var id: String,
    var logsheetID: String,
    var adventureCode: String,
    var adventureName: String,
    var dayMonthYear: String,
    var dmDCInumber: String,
    var startingLevel: String,
    var startingGold: String,
    var startingDowntime: String,
    var startingPermanentMagicItems: String,
    var levelAccepted: String,
    var goldPlusMinus: String,
    var downtimePlusMinus: String,
    var permanentMagicItemsPlusMinus: String,
    var newClassLevel: String,
    var newGoldTotal: String,
    var newDowntimeTotal: String,
    var newPermanentMagicItemTotal: String,
    var adventureNotes: String,
    var soulCoinChargesUsed: String,
    var newSoulCoinChargesUsedTotal: String
)
