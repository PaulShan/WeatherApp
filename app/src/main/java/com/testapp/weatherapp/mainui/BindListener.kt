package com.testapp.weatherapp.mainui

import kotlinx.android.synthetic.main.activity_main.*

fun MainActivity.bindListener() {
    zipCodeEditText.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            updateSearchStatus(QueryMode.ByZipcode)
        }
    }

    cityEditText.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            updateSearchStatus(QueryMode.ByCity)
        }
    }

    latitudeEditText.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            updateSearchStatus(QueryMode.ByLatitudeLongitude)
        }

    }

    longitudeEditText.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            updateSearchStatus(QueryMode.ByLatitudeLongitude)
        }
    }
}