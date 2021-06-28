package com.example.tabletopgames.models

data class DndAlLogSheet(
    var id: String,
    var profileID: String,
    var playerDCInumber: String,
    var characterName: String,
    var characterRace: String,
    var classesANDlevels: String,
    var faction: String,
    var soulCoinsCarried: String,
    var soulCoinChargesUsed: String,
    var logEntries: String
)
