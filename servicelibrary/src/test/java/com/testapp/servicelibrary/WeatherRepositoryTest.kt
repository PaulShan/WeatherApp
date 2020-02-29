package com.testapp.servicelibrary

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.testapp.servicelibrary.apiservice.ApiService
import com.testapp.servicelibrary.country.CountryNameMap
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {
    private lateinit var weatherDataRepository: WeatherRepository

    @Mock
    private lateinit var mockApiService:ApiService
    @Before
    fun setUp() {


    }

    private val testCity = "Sydney"

    private val testCountry = "Australia"

    @Test
    fun `if succeed to get weather data by city, then complete event is received`() {
        //Arrange
        Mockito.`when`(mockApiService.getWeatherDataByCityName(any())).thenReturn(Single.just(TestData.getResponse()))
        weatherDataRepository = WeatherRepository(mockApiService)

        //Action
        val testObserver = weatherDataRepository.getWeatherByCityName(testCity, testCountry).test()
        val cityArgument = argumentCaptor<String>()
        //Assert

        verify(mockApiService).getWeatherDataByCityName(cityArgument.capture())
        val shortName = CountryNameMap.getShortName(testCountry)
        Assert.assertEquals("$testCity,$shortName", cityArgument.lastValue)
        testObserver.assertComplete()
    }


    @Test
    fun `fail to get weather data by city, then error event is received`() {
        //Arrange
        val exception = Exception()
        Mockito.`when`(mockApiService.getWeatherDataByCityName(any())).thenReturn(Single.error(exception))

        //Action
        weatherDataRepository = WeatherRepository(mockApiService)

        //Assert
        val testObserver = weatherDataRepository.getWeatherByCityName(testCity, testCountry).test()
        testObserver.assertError(exception)
    }
}