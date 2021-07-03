package com.example.tabletopgames.models

data class MonopolyLogSheet(
    var id: String,
    var profileID: String,
    var gameType: String,
    var players: String,
    var dayMonthYear: String,
    var logEntries: String
)
