package com.example.tabletopgames

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.RealmConfiguration




lateinit var app: App
const val appId :String = "tabletopgames-suhrr"

class MyApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder()
            // below line is to allow write
            // data to database on ui thread.
            .allowWritesOnUiThread(true)
            // below line is to delete realm
            // if migration is needed.
            .deleteRealmIfMigrationNeeded()
            // at last we are calling a method to build.
            .build()
        // on below line we are setting
        // configuration to our realm database.
        Realm.setDefaultConfiguration(config)

        app = App(AppConfiguration.Builder(appId).defaultSyncErrorHandler { session, error ->
            Log.e(TAG, "Sync error: ${error.errorMessage}")
        }.build())

    }
}