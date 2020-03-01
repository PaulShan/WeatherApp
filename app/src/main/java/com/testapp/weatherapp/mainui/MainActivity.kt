package com.testapp.weatherapp.mainui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.testapp.servicelibrary.country.CountryLongNames
import com.testapp.servicelibrary.models.WeatherBroadcast
import com.testapp.weatherapp.R
import com.testapp.weatherapp.database.QueryItem
import com.testapp.weatherapp.database.QueryItemDao
import com.testapp.weatherapp.database.WeatherDatabase
import com.testapp.weatherapp.recentsearch.RecentSearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), MainActivityInterface {
    override fun renderWeatherData(weatherData: WeatherBroadcast, queryMode: QueryMode) {
        renderWeatherDataExtend(weatherData, queryMode)
    }

    override fun renderErrorForFailToGetWeatherData() {
        progressBar.visibility = View.GONE
        Toast.makeText(this, "Fail to retrieve weather data", Toast.LENGTH_SHORT).show()    }

    override fun updateByQueryItem(queryItem: QueryItem) {
        updateByQueryItemExtend(queryItem)
    }

    override fun provideDao(): QueryItemDao {
        return WeatherDatabase.getInstance(this).queryItemDao()
    }

    val presenter = MainActivityPresenter(this)

    var menu: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        setContentView(R.layout.activity_main)
        weatherItemRecycleView.layoutManager = LinearLayoutManager(this)

        bindListener()

        val arrayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            CountryLongNames.longNames
        )
        countrySpinner.adapter = arrayAdapter


        searchButton.setOnClickListener {
            loadWeatherData()
        }
        setCountry("Australia")

        presenter.loadLastSearch()
    }

    fun getViewModel() = ViewModelProviders.of(this)[WeatherViewModel::class.java]

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clear()
    }

    companion object {
        const val requestRecentSearch = 1
        const val recentSearchDataKey = "recentSearchDataKey"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.recentSearch -> {
                val intent = Intent(this, RecentSearchActivity::class.java)
                startActivityForResult(
                    intent,
                    requestRecentSearch
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestRecentSearch && resultCode == Activity.RESULT_OK) {
            val recentSearchKey = data?.getStringExtra(recentSearchDataKey)
            presenter.loadItem(recentSearchKey)
        }
        return super.onActivityResult(requestCode, resultCode, data)
    }
}
