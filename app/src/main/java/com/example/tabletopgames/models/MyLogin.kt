package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyLogin")
data class MyLogin(
    @PrimaryKey(autoGenerate = false)
    var email: String,
    @ColumnInfo(name = "password")
    var password: String
)