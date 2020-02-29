package com.testapp.servicelibrary.apiservice

import com.testapp.servicelibrary.apiservice.response.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("./")
    fun getWeatherDataByCityName(@Query("q") city: String): Single<WeatherResponse>

    @GET("./")
    fun getWeatherDataByZipCode(@Query("zip") zipCode: String): Single<WeatherResponse>

    @GET("./")
    fun getWeatherDataByGeoLocation(@Query("lat") latitude: Double, @Query("lon") longitude: Double): Single<WeatherResponse>
}