package com.testapp.servicelibrary.models

class WeatherBroadcast(
    val list: List<WeatherItem>,
    val country: String,
    val city: String,
    val latitude: Double?,
    val longitude: Double?
)