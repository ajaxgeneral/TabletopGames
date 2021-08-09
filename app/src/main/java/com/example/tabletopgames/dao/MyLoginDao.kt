package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.MyLogin

@Dao
interface MyLoginDao {
    @Query("select * from MyLogin where email = :email and password = :password")
    suspend fun findMyLogin(email: String,password: String) : MyLogin?
    @Query("select * from MyLogin where email = :email")
    suspend fun emailExists(email: String) : List<MyLogin>?
    @Insert
    suspend fun insert(myLogin: MyLogin)
    @Update
    suspend fun update(myLogin: MyLogin)
    @Delete
    suspend fun delete(myLogin: MyLogin)
}