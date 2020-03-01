package com.testapp.weatherapp.recentsearch.recyclerview

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testapp.weatherapp.R
import com.testapp.weatherapp.database.QueryItemDao
import com.testapp.weatherapp.database.WeatherDatabase
import com.testapp.weatherapp.mainui.MainActivity
import kotlinx.android.synthetic.main.recent_search_item_view.view.*

class RecentSearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val descriptionTextView: TextView = view.contentTextView
    val deleteButton: Button = view.deleteButton
}

class RecentSearchItemRecyclerViewAdapter(private val activity: Activity) :
    RecyclerView.Adapter<RecentSearchItemViewHolder>(),
    RecentSearchItemRecyclerViewAdapterInterface {

    override fun updateView() {
        notifyDataSetChanged()
    }

    override fun provideDao(): QueryItemDao {
        return WeatherDatabase.getInstance(activity).queryItemDao()
    }

    private val presenter = RecentSearchItemRecyclerViewAdapterPresenter(this)

    private val inflater = LayoutInflater.from(activity)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchItemViewHolder {
        val view = inflater.inflate(R.layout.recent_search_item_view, parent, false)
        return RecentSearchItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return presenter.list.size
    }

    override fun onBindViewHolder(holder: RecentSearchItemViewHolder, position: Int) {

        holder.descriptionTextView.text = presenter.getQueryString(position)
        holder.descriptionTextView.setOnClickListener {
            val item = presenter.list[position]
            val intent = Intent().apply {
                putExtra(MainActivity.recentSearchDataKey, item.queryKey)
            }
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
        }

        holder.deleteButton.setOnClickListener {
            presenter.removeAt(position)
        }
    }

    fun clear() {
        presenter.clear()
    }

    fun updateFromDb() {
        presenter.updateFromDb()
    }

}