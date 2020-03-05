package com.testapp.weatherapp.recentsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testapp.weatherapp.database.QueryItem
import com.testapp.weatherapp.database.QueryItemDao
import com.testapp.weatherapp.mainui.QueryMode
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RecentSearchViewModel(private val dao: QueryItemDao) : ViewModel() {

    private val recentSearchItemsImpl: MutableLiveData<List<QueryItemViewModel>> =
        MutableLiveData()
    val recentSearchItems: LiveData<List<QueryItemViewModel>> = recentSearchItemsImpl
    fun deleteByKey(key: String) {
        val convertQuery = dao.deleteByKey(key)
            .toSingleDefault(Any())
            .flatMap {
                dao.getAllQueryItems()
            }

        updateFromDbImpl(convertQuery)

    }

    private fun updateFromDbImpl(convertQuery: Single<List<QueryItem>>) {
        convertQuery.map {
            it.map { item -> item.convert() }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ recentSearchItemsImpl.value = it }, Timber::e)
    }

    fun updateFromDb() {
        val query = dao.getAllQueryItems()
        updateFromDbImpl(query)
    }

}

fun QueryItem.convert() = QueryItemViewModel(getDescription(), queryKey)
private fun QueryItem.getDescription(): String {
    return when (QueryMode.valueOf(queryMode)) {
        QueryMode.ByCity -> "Search $city in $country "
        QueryMode.ByZipcode -> "Search zipcode $zipcode in $country"
        QueryMode.ByLatitudeLongitude -> "Search latitude $latitude longitude $longitude"
    }
}