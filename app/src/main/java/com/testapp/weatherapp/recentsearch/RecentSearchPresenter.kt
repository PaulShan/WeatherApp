package com.testapp.weatherapp.recentsearch

class RecentSearchPresenter(private val recentSearchView: RecentSearchView) {
    fun setup() {
        recentSearchView.setupView()
    }
}