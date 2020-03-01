package com.testapp.weatherapp.recentsearch.recyclerview

import com.testapp.weatherapp.database.QueryItemDao
import io.reactivex.Scheduler

interface RecentSearchItemRecyclerViewAdapterInterface {
    fun provideDao(): QueryItemDao
    fun updateView()
}