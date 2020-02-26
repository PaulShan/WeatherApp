package com.testapp.servicelibrary.apiservice.response

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("city")
    val city: City? = City(),
    @SerializedName("cnt")
    val cnt: Int? = 0,
    @SerializedName("cod")
    val cod: String? = "",
    @SerializedName("list")
    val list: List<WeatherResponseItem?>? = listOf(),
    @SerializedName("message")
    val message: Int? = 0
)

data class City(
    @SerializedName("coord")
    val coord: Coord? = Coord(),
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("sunrise")
    val sunrise: Int? = 0,
    @SerializedName("sunset")
    val sunset: Int? = 0,
    @SerializedName("timezone")
    val timezone: Int? = 0
)

data class Coord(
    @SerializedName("lat")
    val lat: Double? = 0.0,
    @SerializedName("lon")
    val lon: Double? = 0.0
)

data class WeatherResponseItem(
    @SerializedName("clouds")
    val clouds: Clouds? = Clouds(),
    @SerializedName("dt")
    val dt: Long? = 0,
    @SerializedName("dt_txt")
    val dtTxt: String? = "",
    @SerializedName("main")
    val main: Main? = Main(),
    @SerializedName("rain")
    val rain: Rain? = Rain(),
    @SerializedName("snow")
    val snow: Snow? = Snow(),
    @SerializedName("sys")
    val sys: Sys? = Sys(),
    @SerializedName("weather")
    val weather: List<Weather?>? = listOf(),
    @SerializedName("wind")
    val wind: Wind? = Wind()
)

data class Clouds(
    @SerializedName("all")
    val all: Int? = 0
)

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double? = 0.0,
    @SerializedName("grnd_level")
    val grndLevel: Int? = 0,
    @SerializedName("humidity")
    val humidity: Int? = 0,
    @SerializedName("pressure")
    val pressure: Int? = 0,
    @SerializedName("sea_level")
    val seaLevel: Int? = 0,
    @SerializedName("temp")
    val temp: Double? = 0.0,
    @SerializedName("temp_kf")
    val tempKf: Double? = 0.0,
    @SerializedName("temp_max")
    val tempMax: Double? = 0.0,
    @SerializedName("temp_min")
    val tempMin: Double? = 0.0
)

data class Rain(
    @SerializedName("3h")
    val h: Double? = 0.0
)

data class Snow(
    @SerializedName("3h")
    val h: Double? = 0.0
)

data class Sys(
    @SerializedName("pod")
    val pod: String? = ""
)

data class Weather(
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("icon")
    val icon: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("main")
    val main: String? = ""
)

data class Wind(
    @SerializedName("deg")
    val deg: Int? = 0,
    @SerializedName("speed")
    val speed: Double? = 0.0
)