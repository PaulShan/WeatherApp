package com.testapp.weatherapp.recentsearch

import com.testapp.weatherapp.database.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

object MemoryDao {
    private val testCountry = "Australia"

    val testItem1 = createQueryItemByCity("Sydney", testCountry)
    val testItem2 = createQueryItemByZipCode("2001", testCountry)
    val testItem3 = createQueryItemByLatitudeLongitude(-33.8678, 151.2073, testCountry)
    val currentList = mutableListOf(testItem1, testItem2, testItem3)
    fun resetList() {
        currentList.clear()
        currentList.add(testItem1)
        currentList.add(testItem2)
        currentList.add(testItem3)
    }
    fun daoObject(): QueryItemDao {
        return object : QueryItemDao {

            override fun getLatestQueryItem(): Maybe<QueryItem> {
                val last = currentList.lastOrNull()
                return last?.let { Maybe.just(it) } ?: Maybe.empty()

            }

            override fun getQueryItem(searchKey: String): Maybe<QueryItem> {
                val item = currentList.firstOrNull { it.queryKey == searchKey }
                return item?.let { Maybe.just(it) } ?: Maybe.empty()

            }

            override fun getAllQueryItems(): Single<List<QueryItem>> {
                return Single.just(currentList)
            }

            override fun insertAll(vararg queryItem: QueryItem): Completable {
                currentList.addAll(queryItem)
                return Completable.complete()
            }

            override fun delete(queryItem: QueryItem): Completable {
                currentList.remove(queryItem)
                return Completable.complete()
            }

            override fun deleteByKey(searchKey: String): Completable {
                currentList.removeIf { it.queryKey == searchKey }
                return Completable.complete()
            }

        }
    }
}