package com.testapp.servicelibrary

import com.testapp.servicelibrary.apiservice.ApiServiceProvider
import com.testapp.servicelibrary.apiservice.response.WeatherResponse
import com.testapp.servicelibrary.apiservice.response.WeatherResponseItem
import com.testapp.servicelibrary.country.CountryNameMap
import com.testapp.servicelibrary.models.WeatherBroadcast
import com.testapp.servicelibrary.models.WeatherItem
import io.reactivex.Single
import java.util.*
import kotlin.math.roundToInt

class WeatherRepository {
    fun getWeatherByCityName(cityName: String, country: String): Single<WeatherBroadcast> {
        val shortCountryName = CountryNameMap.getShortName(country)
        val cityWithCountry = "$cityName,$shortCountryName"
        return ApiServiceProvider.apiService.getWeatherDataByCityName(cityWithCountry).map { it.convert() }
    }

    fun getWeatherByZipCode(zipcode: String, country: String): Single<WeatherBroadcast> {
        val shortCountryName = CountryNameMap.getShortName(country)
        val zipCodeWithCountry = "$zipcode,$shortCountryName"
        return ApiServiceProvider.apiService.getWeatherDataByZipCode(zipCodeWithCountry).map { it.convert() }
    }

    fun getWeatherByGeoLocation(latitude: Double, longitude: Double): Single<WeatherBroadcast> {
        return ApiServiceProvider.apiService.getWeatherDataByGeoLocation(latitude, longitude)
            .map { it.convert() }
    }
}

fun WeatherResponseItem?.convert(): WeatherItem? {
    return if (this == null)
        null
    else WeatherItem(
        date = dt?.let { date -> Date(date * 1000) } ?: return null,
        icon = weather?.firstOrNull()?.icon,
        description = weather?.firstOrNull()?.description,
        temperature = main?.temp?.roundToInt() ?: return null,
        highTemperature = main.tempMax?.roundToInt() ?: return null,
        lowTemperature = main.tempMin?.roundToInt() ?: return null,
        main = weather?.firstOrNull()?.main ?: return null
    )
}

fun WeatherResponse.convert(): WeatherBroadcast {
    //The null item will be discarded by mapNotNull
    val weatherItemList = list?.mapNotNull {
        it.convert()
    }

    //If any of the items is null, the response is not correct, an exception will be sent and it will be caught by rxjava error handling.
    return WeatherBroadcast(
        list = weatherItemList!!,
        country = city?.country?.let { CountryNameMap.getLongName(it) }!!,
        city = city.name!!,
        latitude = city.coord?.lat,
        longitude = city.coord?.lon
    )
}