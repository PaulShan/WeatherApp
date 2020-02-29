package com.testapp.servicelibrary

import org.junit.Test

class WeatherRepositoryTest {
    private val weatherDataRepository = WeatherRepository()


    @Test
    fun testQueryByCityName() {
        val testObserver = weatherDataRepository.getWeatherByCityName("sydney", "AU").test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
    }

    @Test
    fun testQueryByZipCode() {
        val testObserver = weatherDataRepository.getWeatherByZipCode("2077", "Australia").test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
    }

    @Test
    fun testQueryByGeoLocation() {
        val testObserver =
            weatherDataRepository.getWeatherByGeoLocation(-33.865143, 151.209900).test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
    }
}