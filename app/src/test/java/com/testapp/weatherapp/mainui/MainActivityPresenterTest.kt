package com.testapp.weatherapp.mainui

import com.nhaarman.mockitokotlin2.verify
import com.testapp.weatherapp.database.QueryItem
import com.testapp.weatherapp.database.createQueryItemByCity
import com.testapp.weatherapp.utilities.MemoryDao
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityPresenterTest {
    lateinit var mainActivityPresenter: MainActivityPresenter
    @Mock
    lateinit var view: MainActivityInterface

    @Before
    fun setUp() {
        Mockito.`when`(view.provideDao()).thenReturn(MemoryDao.daoObject())
        mainActivityPresenter = MainActivityPresenter(view)
    }

    @Test
    fun `load latet search and update the view`() {
        mainActivityPresenter.loadLastSearch()

        verify(view).updateByQueryItem(MemoryDao.currentList.last())
    }

    @Test
    fun `load search item1 and update the view`() {
        val searchItem = MemoryDao.testItem1
        loadAndVerify(searchItem)
    }

    @Test
    fun `load search item2 and update the view`() {
        val searchItem = MemoryDao.testItem2
        loadAndVerify(searchItem)
    }

    @Test
    fun `add search item, recent search items will be increased`() {

        val size1 = MemoryDao.daoObject().getAllQueryItems().blockingGet().size
        val searchItem = createQueryItemByCity("Epping", "Australia")

        mainActivityPresenter.addQueryItemToDb(searchItem)

        val size2 = MemoryDao.daoObject().getAllQueryItems().blockingGet().size

        assertEquals(size1 + 1, size2)
    }


    private fun loadAndVerify(searchItem: QueryItem) {
        mainActivityPresenter.loadItem(searchItem.queryKey)
        verify(view).updateByQueryItem(searchItem)
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