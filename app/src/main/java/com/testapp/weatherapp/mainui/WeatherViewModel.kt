package com.testapp.weatherapp.mainui

import androidx.lifecycle.ViewModel
import com.testapp.servicelibrary.models.WeatherBroadcast

//The data survive after the screen is rotated.
enum class QueryMode {
    ByZipcode, ByCity, ByLatitudeLongitude;

    companion object {
        fun valueOf(ordinal: Int) = values()[ordinal]
    }
}

class WeatherViewModel : ViewModel() {
    var weatherDataManager: WeatherBroadcast? = null
    var queryMode: QueryMode =
        QueryMode.ByZipcode
}