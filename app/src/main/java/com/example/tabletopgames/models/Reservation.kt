package com.example.tabletopgames.models

data class Reservation(
    var id: String,
    var profileID: String,
    var gameType: String,
    var dayMonthYear: String,
    var time: String,
    var table: String,
    var seat: String,
    var duration: String
)
