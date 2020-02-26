package com.testapp.weatherapp.mainui

import android.view.View
import com.testapp.servicelibrary.country.CountryLongNames
import com.testapp.servicelibrary.models.WeatherBroadcast
import com.testapp.weatherapp.R
import com.testapp.weatherapp.database.QueryItem
import kotlinx.android.synthetic.main.activity_main.*

fun MainActivity.updateByQueryItem(it: QueryItem) {
    val queryMode =
        QueryMode.valueOf(it.queryMode)
    updateSearchStatus(queryMode)
    setCountry(it.country)
    cityEditText.setText(it.city)
    zipCodeEditText.setText(it.zipcode)
    latitudeEditText.setText(it.latitude?.toString())
    longitudeEditText.setText(it.longitude?.toString())
    loadWeatherData()
}

fun MainActivity.setCountry(country: String) {
    val index = CountryLongNames.longNames.indexOf(country)
    countrySpinner.setSelection(index)
}

fun MainActivity.updateSearchStatus(queryMode: QueryMode) {
    getViewModel().queryMode = queryMode
    searchButton.text = when (queryMode) {
        QueryMode.ByZipcode -> "Search By zipcode"
        QueryMode.ByCity -> "Search By city"
        QueryMode.ByLatitudeLongitude -> "Search By lat lng"
    }
}

fun MainActivity.renderWeatherData(
    weatherData: WeatherBroadcast,
    queryMode: QueryMode
) {
    weatherItemRecycleView.adapter =
        WeatherItemRecyclerViewAdapter(
            this,
            weatherData
        )
    getViewModel().weatherDataManager = weatherData
    progressBar.visibility = View.GONE
    cityEditText.setText(weatherData.city)
    latitudeEditText.setText(weatherData.latitude.toString())
    longitudeEditText.setText(weatherData.longitude.toString())
    setCountry(weatherData.country)

    if (queryMode != QueryMode.ByZipcode) {
        zipCodeEditText.setText("")
    }

    menu?.findItem(R.id.recentSearch)?.isVisible = true
}

fun MainActivity.resetErrorStatus() {
    zipCodeEditText.error = null
    cityEditText.error = null
    latitudeEditText.error = null
    longitudeEditText.error = null
}