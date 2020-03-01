package com.testapp.weatherapp.mainui

import com.testapp.servicelibrary.models.WeatherBroadcast
import com.testapp.weatherapp.database.QueryItem
import com.testapp.weatherapp.database.QueryItemDao

interface MainActivityInterface {
    fun provideDao(): QueryItemDao
    fun updateByQueryItem(queryItem: QueryItem)
    fun renderWeatherData(weatherData: WeatherBroadcast,
                          queryMode: QueryMode)

    fun renderErrorForFailToGetWeatherData()
}