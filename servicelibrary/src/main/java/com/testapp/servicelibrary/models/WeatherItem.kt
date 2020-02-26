package com.testapp.servicelibrary.models

import java.util.*

data class WeatherItem(
    val date: Date,
    val icon: String?,
    val description: String?,
    val main:String,
    val temperature: Int,
    val highTemperature:Int,
    val lowTemperature:Int
)