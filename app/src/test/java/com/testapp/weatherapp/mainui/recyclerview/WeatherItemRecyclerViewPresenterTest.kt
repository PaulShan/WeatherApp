package com.testapp.weatherapp.mainui.recyclerview

import android.view.View
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.testapp.servicelibrary.models.WeatherBroadcast
import com.testapp.servicelibrary.models.WeatherItem
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class WeatherItemRecyclerViewPresenterTest {
    lateinit var presenter: WeatherItemRecyclerViewPresenter

    @Before
    fun setUp() {

        presenter = WeatherItemRecyclerViewPresenter(weatherBroadcast)
    }

    @Test
    fun `get the data by index`() {
        val index = 0
        val item = presenter.getItem(index)

        assertEquals(weatherBroadcast.list[index], item)
    }

    val weatherBroadcast = WeatherBroadcast(
        country = "Australia",
        city = "Sydney",
        latitude = -33.8678,
        longitude = 151.2073,
        list = listOf(
            WeatherItem(
                date = Date(),
                icon = "04h",
                description = "broken clouds",
                main = "Clouds",
                temperature = 22,
                highTemperature = 29,
                lowTemperature = 19
            )
        )
    )
}