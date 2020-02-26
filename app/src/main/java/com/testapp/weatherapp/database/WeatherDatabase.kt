package com.testapp.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testapp.weatherapp.utilities.SingletonHolder

@Database(entities = [QueryItem::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun queryItemDao(): QueryItemDao

    companion object : SingletonHolder<WeatherDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            WeatherDatabase::class.java, "Weather.db"
        ).fallbackToDestructiveMigration()
            .build()
    })
}