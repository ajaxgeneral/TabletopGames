package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.MyProfile

@Dao
interface MyProfileDao {
    @Query("select * from MyProfile where email = :email")
    suspend fun getMyProfile(email:String) : MyProfile?
    @Query("select * from MyProfile where id = :id")
    suspend fun getMyProfileByID(id: Int) : MyProfile?
    @Insert
    suspend fun insert(myProfile: MyProfile)
    @Update
    suspend fun update(myProfile: MyProfile)
    @Delete
    suspend fun delete(myProfile: MyProfile)
}