package com.example.tabletopgames.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyProfile")
data class MyProfile(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "firstName")
    var firstName: String,
    @ColumnInfo(name = "lastName")
    var lastName: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "phone")
    var phone: String,
    @ColumnInfo(name = "userType")
    var userType: User
)
