package com.testapp.weatherapp.mainui

import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.testapp.weatherapp.database.createQueryItemByCity
import com.testapp.weatherapp.database.createQueryItemByLatitudeLongitude
import com.testapp.weatherapp.database.createQueryItemByZipCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

fun MainActivity.loadWeatherData() {
    resetErrorStatus()
    val country = countrySpinner.selectedItem.toString()
    val city = cityEditText.text.toString()
    val zipcode = zipCodeEditText.text.toString()
    val latitude = latitudeEditText.text.toString().toDoubleOrNull()
    val longitude = longitudeEditText.text.toString().toDoubleOrNull()

    val queryMode = getViewModel().queryMode
    val querySingle = when (queryMode) {
        QueryMode.ByCity -> {
            if (city.isBlank() || city.isDigitsOnly()) {
                cityEditText.error = "City is empty or only digits"
                return
            }
            val queryItem = createQueryItemByCity(city, country)
            addQueryItemToDb(queryItem)
            repository.getWeatherByCityName(city, country)
        }
        QueryMode.ByZipcode -> {
            if (zipcode.isBlank()) {
                zipCodeEditText.error = "zip code cannot be empty."
                return
            }

            val queryItem = createQueryItemByZipCode(zipcode, country)
            addQueryItemToDb(queryItem)
            repository.getWeatherByZipCode(zipcode, country)
        }
        QueryMode.ByLatitudeLongitude -> {
            if (latitude == null) {
                latitudeEditText.error = "Latitude need to be a number"
                return
            }

            if (longitude == null) {
                longitudeEditText.error = "Longitude need to be a number"
                return
            }

            val queryItem = createQueryItemByLatitudeLongitude(latitude, longitude, country)
            addQueryItemToDb(queryItem)

            repository.getWeatherByGeoLocation(
                latitude,
                longitude
            )
        }
    }
    progressBar.visibility = View.VISIBLE

    val disposable = querySingle
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ weatherData ->
            renderWeatherData(weatherData, queryMode)

        }, {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Fail to retrieve weather data", Toast.LENGTH_SHORT).show()
        })
    compositeDisposable.add(disposable)
}

