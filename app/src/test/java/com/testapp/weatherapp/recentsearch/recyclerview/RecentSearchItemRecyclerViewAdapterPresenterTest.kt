package com.testapp.weatherapp.recentsearch.recyclerview

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.testapp.weatherapp.utilities.MemoryDao
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
        Assert.assertArrayEquals(MemoryDao.currentList.toTypedArray(), presenter.list.toTypedArray())
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
        MemoryDao.resetList()
        presenter.updateFromDb()

        Assert.assertEquals("Search Sydney in Australia", presenter.getQueryString(0))
        Assert.assertEquals("Search zipcode 2001 in Australia", presenter.getQueryString(1))
        Assert.assertEquals("Search latitude -33.8678 longitude 151.2073", presenter.getQueryString(2))
    }


    @Before
    fun setUp() {
        presenter = RecentSearchItemRecyclerViewAdapterPresenter(view)
        Mockito.`when`(view.provideDao()).thenReturn(MemoryDao.daoObject())
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }
    }
}