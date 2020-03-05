package com.testapp.weatherapp.recentsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.testapp.weatherapp.R
import com.testapp.weatherapp.database.QueryItemDao
import com.testapp.weatherapp.database.WeatherDatabase
import kotlinx.android.synthetic.main.activity_recent_search.*

class RecentSearchActivity : AppCompatActivity() {

    private lateinit var recyclerViewAdapter: RecentSearchItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_search)
        val viewModel = getViewModel()
        recyclerViewAdapter =
            RecentSearchItemRecyclerViewAdapter(
                this, viewModel
            )
        recentSearchItemRecycleView.layoutManager = LinearLayoutManager(this)
        recentSearchItemRecycleView.adapter = recyclerViewAdapter


        val observer = Observer<List<QueryItemViewModel>> {
            recyclerViewAdapter.setData(it)
        }

        viewModel.recentSearchItems.observe(this, observer)
        viewModel.updateFromDb()
    }

    private val dao: QueryItemDao by lazy {
        WeatherDatabase.getInstance(this).queryItemDao()
    }

    private fun getViewModel() = ViewModelProviders.of(
        this,
        RecentSearchViewModelFactory(dao)
    )[RecentSearchViewModel::class.java]


}
