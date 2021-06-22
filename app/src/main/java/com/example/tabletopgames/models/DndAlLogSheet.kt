package com.example.tabletopgames.models

data class DndAlLogSheet(
    var id: String,
    var profileID: String,
    var playerDCInumber: String,
    var characterRace: String,
    var classesANDlevels: List<ClassesANDLevels>,
    var faction: String,
    var soulCoinsCarried: String,
    var soulCoinChargesUsed: String,
    var logEntries: List<DndAlEntry>
)
