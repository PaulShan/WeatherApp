package com.testapp.weatherapp.recentsearch

import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecentSearchPresenterTest {
    @Mock lateinit var view : RecentSearchView
    @Test
    fun `setup presenter will setup the view`() {
        val presenter = RecentSearchPresenter(view)

        presenter.setup()

        verify(view).setupView()
    }
}