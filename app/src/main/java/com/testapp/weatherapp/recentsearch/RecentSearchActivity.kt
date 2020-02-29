package com.testapp.weatherapp.recentsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.testapp.weatherapp.R
import kotlinx.android.synthetic.main.activity_recent_search.*

class RecentSearchActivity : AppCompatActivity(), RecentSearchView {
    private val recentSearchPresenter = RecentSearchPresenter(this)
    override fun setupView() {
        recyclerViewAdapter =
            RecentSearchItemRecyclerViewAdapter(
                this
            )
        recentSearchItemRecycleView.layoutManager = LinearLayoutManager(this)
        recentSearchItemRecycleView.adapter = recyclerViewAdapter
        recyclerViewAdapter.updateFromDb()
    }

    internal lateinit var recyclerViewAdapter: RecentSearchItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_search)
        recentSearchPresenter.setup()
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerViewAdapter.clear()
    }
}
