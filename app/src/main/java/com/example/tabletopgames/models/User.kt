package com.example.tabletopgames.models

import io.realm.RealmObject

open class User : RealmObject() {
    var _id = 0
    var email: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var password: String? = null
    var type: String? = null
}