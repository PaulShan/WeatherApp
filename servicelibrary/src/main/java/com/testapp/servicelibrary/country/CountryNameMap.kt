package com.testapp.servicelibrary.country

object CountryNameMap {
    private val shortNameToLongNameMap: Map<String, String> by lazy {
        CountryShortNames.shortNames.zip(CountryLongNames.longNames).toMap()
    }

    private val longNameToShortNameMap: Map<String, String> by lazy {
        CountryLongNames.longNames.zip(CountryShortNames.shortNames).toMap()
    }

    fun getShortName(longName: String): String? = longNameToShortNameMap[longName]
    fun getLongName(shortName: String): String? = shortNameToLongNameMap[shortName]
}