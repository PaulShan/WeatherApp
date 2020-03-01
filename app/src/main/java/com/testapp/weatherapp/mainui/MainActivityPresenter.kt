package com.testapp.weatherapp.mainui

import com.testapp.servicelibrary.WeatherRepository
import com.testapp.servicelibrary.models.WeatherBroadcast
import com.testapp.weatherapp.database.QueryItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainActivityPresenter(val view: MainActivityInterface, private val repository:WeatherRepository = WeatherRepository()) {

    private val compositeDisposable = CompositeDisposable()

    fun loadLastSearch() {
        val d = view.provideDao().getLatestQueryItem()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::updateByQueryItem, Timber::e)

        compositeDisposable.add(d)

    }

    fun loadItem(recentSearchKey: String?) {
        if (recentSearchKey == null) return
        val d = view.provideDao().getQueryItem(recentSearchKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::updateByQueryItem, Timber::e)
        compositeDisposable.add(d)
    }

    fun addQueryItemToDb(queryItem: QueryItem) {
        val d = view.provideDao().insertAll(queryItem)
            .subscribeOn(Schedulers.io())
            .subscribe({}, Timber::e)
        compositeDisposable.add(d)
    }

    fun getWeatherByCityName(city: String, country: String) =
        repository.getWeatherByCityName(city, country)

    fun getWeatherByZipCode(zipcode: String, country: String) =
        repository.getWeatherByZipCode(zipcode, country)

    fun getWeatherByGeoLocation(latitude: Double, longitude: Double) =
        repository.getWeatherByGeoLocation(latitude, longitude)

    fun subscribeWeatherData(querySingle: Single<WeatherBroadcast>, queryMode: QueryMode) {
        val disposable = querySingle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ weatherData ->
                view.renderWeatherData(weatherData, queryMode)

            }, {
                view.renderErrorForFailToGetWeatherData()
            })
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }
}


