package com.example.tabletopgames.models

import io.realm.RealmObject
import java.sql.Time
import java.util.*

open class Reservation : RealmObject(){
    var _id = 0
    var date: String? = null
    var time: String? = null
    var duration: String? = null
    var game_type: String? = null
    var table: String? = null
    var seat: String? = null
    var user_id: String? = null
}