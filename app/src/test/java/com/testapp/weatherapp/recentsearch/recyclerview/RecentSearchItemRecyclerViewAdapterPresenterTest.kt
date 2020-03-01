package com.testapp.weatherapp.recentsearch.recyclerview

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.testapp.weatherapp.database.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecentSearchItemRecyclerViewAdapterPresenterTest {

    @Mock
    lateinit var view: RecentSearchItemRecyclerViewAdapterInterface

    lateinit var presenter: RecentSearchItemRecyclerViewAdapterPresenter


    @Test
    fun `update from db, view will be updated`() {
        presenter.updateFromDb()

        verify(view).updateView()
        Assert.assertArrayEquals(arrayOf(testItem1, testItem2, testItem3), presenter.list.toTypedArray())
    }

    @Test
    fun `remove item from db, view will be updated`() {
        presenter.updateFromDb()
        presenter.removeAt(0)
        verify(view, times(2)).updateView() //Get data and remove, each time will update the view.
    }

    @Test
    fun `remove the single item from db, the list in presenter will be empty`() {
        presenter.updateFromDb()
        val itemNumber = presenter.list.size

        presenter.removeAt(0)
        Assert.assertEquals(itemNumber - 1, presenter.list.size)
    }

    @Test
    fun `update from db, check the text`() {
        presenter.updateFromDb()

        Assert.assertEquals("Search Sydney in Australia", presenter.getQueryString(0))
        Assert.assertEquals("Search zipcode 2001 in Australia", presenter.getQueryString(1))
        Assert.assertEquals("Search latitude -33.8678 longitude 151.2073", presenter.getQueryString(2))
    }

    private val testCountry = "Australia"

    private val testItem1 = createQueryItemByCity("Sydney", testCountry)
    private val testItem2 = createQueryItemByZipCode("2001", testCountry)
    private val testItem3 = createQueryItemByLatitudeLongitude(-33.8678, 151.2073, testCountry)



    @Before
    fun setUp() {
        presenter = RecentSearchItemRecyclerViewAdapterPresenter(view)
        val currentList = mutableListOf(testItem1, testItem2, testItem3)
        Mockito.`when`(view.provideDao()).thenReturn(object : QueryItemDao {
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

        })
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }
    }
}