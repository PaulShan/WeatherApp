package com.testapp.weatherapp.mainui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.testapp.servicelibrary.WeatherRepository
import com.testapp.servicelibrary.country.CountryLongNames
import com.testapp.weatherapp.R
import com.testapp.weatherapp.recentsearch.RecentSearchActivity
import com.testapp.weatherapp.database.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    val repository = WeatherRepository()
    val compositeDisposable = CompositeDisposable()
    lateinit var dao: QueryItemDao
    var menu: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        setContentView(R.layout.activity_main)
        weatherItemRecycleView.layoutManager = LinearLayoutManager(this)
        dao = WeatherDatabase.getInstance(this).queryItemDao()
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
        val d = dao.getLatestQueryItem()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::updateByQueryItem, Timber::e)

        compositeDisposable.add(d)
    }

    fun getViewModel() = ViewModelProviders.of(this)[WeatherViewModel::class.java]

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
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
            if (recentSearchKey != null) {
                val d = dao.getQueryItem(recentSearchKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(::updateByQueryItem, Timber::e)
                compositeDisposable.add(d)
            }
        }
        return super.onActivityResult(requestCode, resultCode, data)
    }
}
