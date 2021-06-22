package com.example.tabletopgames.models

data class MonopolyLogSheet(
    var id: String,
    var profileID: String,
    var players: List<String>,
    var logEntries: List<MonopolyEntry>
)
