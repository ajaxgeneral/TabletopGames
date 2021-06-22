package com.example.tabletopgames.models

data class MtgEntry(
    var id: String,
    var logsheetID: String,
    var winner: List<String>
)
