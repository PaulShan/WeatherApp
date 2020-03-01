package com.testapp.weatherapp.mainui.recyclerview

import com.testapp.servicelibrary.models.WeatherBroadcast

class WeatherItemRecyclerViewPresenter(
    private val weatherBroadcast: WeatherBroadcast
) {
    fun size() = weatherBroadcast.list.size
    fun getItem(index: Int) = weatherBroadcast.list[index]
}