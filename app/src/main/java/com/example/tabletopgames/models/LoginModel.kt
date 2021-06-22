package com.example.tabletopgames.models

import io.realm.RealmObject

open class LoginModel(
    var id: String,
    var email: String,
    var password: String
): RealmObject() {
    constructor():this("","","")
}
