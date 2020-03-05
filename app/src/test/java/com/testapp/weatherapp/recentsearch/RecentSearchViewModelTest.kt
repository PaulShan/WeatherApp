package com.testapp.weatherapp.recentsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.rules.TestRule

class RecentSearchViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var recentSearchViewModel: RecentSearchViewModel
    @Before
    fun setUp() {
        recentSearchViewModel = RecentSearchViewModel(MemoryDao.daoObject())
    }

    @Test
    fun deleteByKey() {
        recentSearchViewModel.deleteByKey(MemoryDao.testItem1.queryKey)
        recentSearchViewModel.recentSearchItems.observeForever { }

        Assert.assertArrayEquals(
            recentSearchViewModel.recentSearchItems.value!!.toTypedArray(),
            MemoryDao.currentList.map { it.convert() }.toTypedArray()
        )
    }

    @Test
    fun updateFromDb() {
        recentSearchViewModel.updateFromDb()
        recentSearchViewModel.recentSearchItems.observeForever { }

        Assert.assertArrayEquals(
            recentSearchViewModel.recentSearchItems.value!!.toTypedArray(),
            MemoryDao.currentList.map { it.convert() }.toTypedArray()
        )
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