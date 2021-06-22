package com.example.tabletopgames.models

data class MTGlogsheet(
    var id: String,
    var profileID: String,
    var players: List<String>,
    var dayMonthYear: String,
    var logEntries: List<MtgEntry>
)
