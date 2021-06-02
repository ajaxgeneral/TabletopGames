package com.example.tabletopgames

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

lateinit var app: App
const val appId :String = "tabletopgames-suhrr"

class MyApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        app = App(AppConfiguration.Builder(appId).defaultSyncErrorHandler { session, error ->
            Log.e(TAG, "Sync error: ${error.errorMessage}")
        }.build())

    }
}