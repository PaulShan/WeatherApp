package com.testapp.servicelibrary.apiservice.gsonprovider

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonProvider {
    val gson: Gson by lazy {
        provideGson()
    }

    private fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }
}