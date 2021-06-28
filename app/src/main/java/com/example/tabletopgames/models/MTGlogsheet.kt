package com.example.tabletopgames.models

data class MTGlogsheet(
    var id: String,
    var profileID: String,
    var players: String,
    var dayMonthYear: String,
    var logEntries: String
)
