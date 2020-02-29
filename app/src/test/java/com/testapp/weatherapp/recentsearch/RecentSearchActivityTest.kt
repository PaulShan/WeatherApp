package com.testapp.weatherapp.recentsearch

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RecentSearchActivityTest
{

    @Test
    fun createRecyclerViewAdapter() {
        val controller = buildActivity<RecentSearchActivity>(RecentSearchActivity::class.java).setup()

        Assert.assertNotNull(controller.get().recyclerViewAdapter)
    }
}