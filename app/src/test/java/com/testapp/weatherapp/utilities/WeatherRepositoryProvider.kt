package com.testapp.weatherapp.utilities

import com.nhaarman.mockitokotlin2.any
import com.testapp.servicelibrary.WeatherRepository
import com.testapp.servicelibrary.apiservice.ApiService
import io.reactivex.Single
import org.mockito.Mockito

object WeatherRepositoryProvider {
    fun provideNormalRepository(): WeatherRepository {
        val mockApiService = Mockito.mock(ApiService::class.java)

        Mockito.`when`(mockApiService.getWeatherDataByCityName(any())).thenReturn(
            Single.just(
                TestData.getResponse()
            )
        )

        Mockito.`when`(mockApiService.getWeatherDataByZipCode(any())).thenReturn(
            Single.just(
                TestData.getResponse()
            )
        )

        Mockito.`when`(mockApiService.getWeatherDataByGeoLocation(any(), any())).thenReturn(
            Single.just(
                TestData.getResponse()
            )
        )

        return WeatherRepository(mockApiService)
    }
    val exception = Exception()
    fun provideErrorRepository(): WeatherRepository {
        val mockApiService = Mockito.mock(ApiService::class.java)

        Mockito.`when`(mockApiService.getWeatherDataByCityName(any())).thenReturn(
            Single.error(exception)
        )

        Mockito.`when`(mockApiService.getWeatherDataByZipCode(any())).thenReturn(
            Single.error(exception)
        )

        Mockito.`when`(mockApiService.getWeatherDataByGeoLocation(any(), any())).thenReturn(
            Single.error(exception)
        )

        return WeatherRepository(mockApiService)
    }
}