package com.testapp.weatherapp.mainui

import com.testapp.weatherapp.database.QueryItem
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

fun MainActivity.addQueryItemToDb(queryItem: QueryItem) {
    val d = dao.insertAll(queryItem)
        .subscribeOn(Schedulers.io())
        .subscribe({}, Timber::e)
    compositeDisposable.add(d)
}