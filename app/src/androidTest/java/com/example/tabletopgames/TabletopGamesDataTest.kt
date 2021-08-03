package com.example.tabletopgames

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.tabletopgames.dao.MyLoginDao
import com.example.tabletopgames.database.TabletopGamesDatabase
import com.example.tabletopgames.models.MyLogin
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TabletopGamesDataTest {
    private lateinit var myLoginDao: MyLoginDao
    private lateinit var db: TabletopGamesDatabase

    @Before
    fun createDb(){
        val context =
            InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context,
            TabletopGamesDatabase::class.java).allowMainThreadQueries().build()
        myLoginDao = db.myLoginDao()
    }
    @After
    @Throws(IOException::class)
    fun deleteDb(){
        db.close()
    }
    @Test
    @Throws(Exception::class)
    fun insertAndGetMyLogin() = runBlocking {
        val myLogin = MyLogin("test@gmail.com","password")
        myLoginDao.insert(myLogin)
        val oneLogin = myLoginDao.findMyLogin(myLogin.email,myLogin.password)
        assertEquals(oneLogin?.email,"test@gmail.com")
    }
}