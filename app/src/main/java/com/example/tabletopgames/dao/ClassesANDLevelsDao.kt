package com.example.tabletopgames.dao

import androidx.room.*
import com.example.tabletopgames.models.ClassesANDLevels
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassesANDLevelsDao {
    @Query("select * from ClassesANDLevels where profileID = :profileID and logsheetID = :logsheetID")
    fun getAllClassesFor(profileID: Int,logsheetID: Int) : List<ClassesANDLevels>?
    @Insert
    suspend fun insert(classesANDlevels:ClassesANDLevels)
    @Update
    suspend fun update(classesANDlevels: ClassesANDLevels)
    @Delete
    suspend fun delete(classesANDlevels: ClassesANDLevels)
}