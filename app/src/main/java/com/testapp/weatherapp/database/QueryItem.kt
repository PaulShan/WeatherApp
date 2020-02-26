package com.testapp.weatherapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.testapp.weatherapp.mainui.QueryMode

@Entity(tableName = "QueryItemTable")
class QueryItem(
    @PrimaryKey val queryKey: String,
    val queryMode: Int,
    val country: String, //Can be nullable, but we always have current selected country.
    val generatedTime: Long,
    val city: String? = null,
    val zipcode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

fun createQueryItemByCity(city: String, country: String): QueryItem {

    val queryMode = QueryMode.ByCity.ordinal
    val time = System.currentTimeMillis()
    val queryKey = "$queryMode,$city,$country"
    return QueryItem(
        queryKey = queryKey,
        queryMode = queryMode,
        generatedTime = time,
        city = city,
        country = country
    )
}

fun createQueryItemByZipCode(zipcode: String, country: String): QueryItem {
    val queryMode = QueryMode.ByZipcode.ordinal
    val time = System.currentTimeMillis()
    val queryKey = "$queryMode,$zipcode,$country"
    return QueryItem(
        queryKey = queryKey,
        queryMode = queryMode,
        generatedTime = time,
        zipcode = zipcode,
        country = country
    )
}

fun createQueryItemByLatitudeLongitude(
    latitude: Double,
    longitude: Double,
    country: String
): QueryItem {
    val queryMode = QueryMode.ByLatitudeLongitude.ordinal
    val time = System.currentTimeMillis()
    val queryKey = "$queryMode,$latitude,$longitude"
    return QueryItem(
        queryKey = queryKey,
        queryMode = queryMode,
        generatedTime = time,
        latitude = latitude,
        longitude = longitude,
        country = country
    )
}


