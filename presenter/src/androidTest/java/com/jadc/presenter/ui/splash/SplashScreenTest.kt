package com.jadc.presenter.ui.splash

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.jadc.presenter.R
import com.jadc.presenter.utils.ConnectivityObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun no_internet_connection_shows_network_error() {
        composeTestRule.setContent {
            SplashStateless(ConnectivityObserver.NetworkStatus.Unavailable)
        }

        val title = context.getString(R.string.no_internet_connection)
        val errorText = context.getString(R.string.please_check_your_internet)
        composeTestRule.onNodeWithText(title).assertExists()
        composeTestRule.onNodeWithText(errorText).assertExists()
    }
}