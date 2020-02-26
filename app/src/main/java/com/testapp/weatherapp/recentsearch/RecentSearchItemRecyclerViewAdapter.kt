package com.testapp.weatherapp.recentsearch

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testapp.weatherapp.R
import com.testapp.weatherapp.database.QueryItem
import com.testapp.weatherapp.database.WeatherDatabase
import com.testapp.weatherapp.mainui.MainActivity
import com.testapp.weatherapp.mainui.QueryMode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recent_search_item_view.view.*
import timber.log.Timber

class RecentSearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val descriptionTextView: TextView = view.contentTextView
    val deleteButton: Button = view.deleteButton
}

class RecentSearchItemRecyclerViewAdapter(private val activity: Activity) :
    RecyclerView.Adapter<RecentSearchItemViewHolder>() {
    private val compositeDisposable = CompositeDisposable()

    private val dao = WeatherDatabase.getInstance(activity).queryItemDao()
    private val inflater = LayoutInflater.from(activity)
    private var list: List<QueryItem> = listOf()
    fun updateFromDb() {
        val d = dao
            .getAllQueryItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list = it
                notifyDataSetChanged()

            }, Timber::e)

        compositeDisposable.add(d)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchItemViewHolder {
        val view = inflater.inflate(R.layout.recent_search_item_view, parent, false)
        return RecentSearchItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecentSearchItemViewHolder, position: Int) {
        val item = list[position]
        val queryMode = QueryMode.valueOf(item.queryMode)

        holder.descriptionTextView.text = when (queryMode) {
            QueryMode.ByCity -> "Search ${item.city} in ${item.country} "
            QueryMode.ByZipcode -> "Search zipcode ${item.zipcode} in ${item.country}"
            QueryMode.ByLatitudeLongitude -> "Search latitude ${item.latitude} longitude${item.longitude}"
        }

        holder.descriptionTextView.setOnClickListener {
            val intent = Intent().apply {
                this.putExtra(MainActivity.recentSearchDataKey, item.queryKey)
            }
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
        }

        holder.deleteButton.setOnClickListener {
            dao
                .delete(item)
                .toSingleDefault(Any())
                .flatMap { dao.getAllQueryItems() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list = it
                    notifyDataSetChanged()

                }, Timber::e)

            updateFromDb()
        }
    }

    fun clear() {
        compositeDisposable.clear()
    }

}