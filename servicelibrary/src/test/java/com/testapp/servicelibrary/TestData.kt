package com.testapp.servicelibrary

import com.google.gson.Gson
import com.testapp.servicelibrary.apiservice.response.WeatherResponse
import java.io.File

object TestData {
    val gson = Gson()
    fun getResponse(): WeatherResponse {
        val file = File("src/test/java/com/testapp/servicelibrary/Response.json")
        val text = file.readText()
        return gson.fromJson<WeatherResponse>(text, WeatherResponse::class.java)
    }
}