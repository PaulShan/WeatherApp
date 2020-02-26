package com.testapp.weatherapp.mainui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.testapp.servicelibrary.models.WeatherBroadcast
import com.testapp.weatherapp.R
import kotlinx.android.synthetic.main.weather_item_view.view.*
import java.text.SimpleDateFormat

class WeatherItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.weatherIconImageView
    val dateTextView: TextView = view.dateTextView
    val descriptionTextView: TextView = view.descriptionTextView
    val temperatureTextView: TextView = view.temperatureTextView
}

class WeatherItemRecyclerViewAdapter(
    context: Context,
    private val weatherItemManager: WeatherBroadcast
) :

    RecyclerView.Adapter<WeatherItemViewHolder>() {
    val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemViewHolder {
        val view = inflater.inflate(R.layout.weather_item_view, parent, false)
        return WeatherItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherItemManager.list.size
    }

    override fun onBindViewHolder(holder: WeatherItemViewHolder, position: Int) {
        val item = weatherItemManager.list[position]
        holder.dateTextView.text = formatter.format(item.date)
        holder.descriptionTextView.text = item.description
        holder.temperatureTextView.text = "${item.temperature} °C"
        holder.imageView.contentDescription = item.description
        val url = "http://openweathermap.org/img/wn/${item.icon}@2x.png"
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView)
    }
}