package com.testapp.weatherapp.database

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class WeatherDatabaseTest {
    private lateinit var queryDao: QueryItemDao
    private lateinit var db: WeatherDatabase

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            appContext, WeatherDatabase::class.java).build()
        queryDao = db.queryItemDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeItemToDbAndReadit() {
        val queryItem = createQueryItemByZipCode("2120", "Australia")
        queryDao.insertAll(queryItem).test()
        val testObserver = queryDao.getLatestQueryItem().test()
        val itemFromDb = testObserver.values().first()
        Assert.assertEquals(queryItem.zipcode, itemFromDb.zipcode)
    }
}