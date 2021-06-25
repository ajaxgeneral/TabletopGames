package com.example.tabletopgames.models

import io.realm.RealmObject

open class Profile(
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String
) : RealmObject() {
    constructor() : this("","","","","")
}
