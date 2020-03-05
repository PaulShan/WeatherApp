package com.testapp.weatherapp.recentsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testapp.weatherapp.database.QueryItemDao

class RecentSearchViewModelFactory(val dao: QueryItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return RecentSearchViewModel(dao) as T
    }
}