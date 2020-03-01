package com.testapp.weatherapp.recentsearch.recyclerview

import com.testapp.weatherapp.database.QueryItem
import com.testapp.weatherapp.mainui.QueryMode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RecentSearchItemRecyclerViewAdapterPresenter(private val view: RecentSearchItemRecyclerViewAdapterInterface) {
    var list: List<QueryItem> = listOf()
    private val compositeDisposable = CompositeDisposable()

    fun updateFromDb() {
        val d = view.provideDao()
            .getAllQueryItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list = it
                view.updateView()

            }, Timber::e)

        compositeDisposable.add(d)

    }

    fun removeAt(index: Int) {
        val item = list[index]

        val d = view.provideDao()
            .delete(item)
            .toSingleDefault(Any())
            .flatMap { view.provideDao().getAllQueryItems() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list = it
                view.updateView()

            }, Timber::e)

        compositeDisposable.add(d)

    }

    fun getQueryString(index: Int): String {
        val item = list[index]
        val queryMode = QueryMode.valueOf(item.queryMode)

        return when (queryMode) {
            QueryMode.ByCity -> "Search ${item.city} in ${item.country}"
            QueryMode.ByZipcode -> "Search zipcode ${item.zipcode} in ${item.country}"
            QueryMode.ByLatitudeLongitude -> "Search latitude ${item.latitude} longitude ${item.longitude}"
        }
    }

    fun clear() {
        compositeDisposable.clear()
    }
}