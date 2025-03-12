package com.jadc.presenter.ui.product

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jadc.presenter.MainActivity
import com.mvvm.jadc.domain.model.ProductDetailUI
import com.mvvm.jadc.domain.model.ProductUI
import io.mockk.coEvery
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin

@RunWith(AndroidJUnit4::class)
class ProductScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeProduct = ProductDetailUI(
        id = "MCO1",
        name = "fake product",
        pictures = emptyList(),
        price = 1000000.0,
        stock = 100,
        attributes = emptyList()
    )

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun test() {
        val portraitWindowSize = DpSize(360.dp, 640.dp)
        val fakeWindowSize = WindowSizeClass.calculateFromSize(portraitWindowSize)

        composeTestRule.setContent {
            ProductStateless(
                product = fakeProduct,
                windowSizeClass = fakeWindowSize,
                onBack = {}
            )
        }

        composeTestRule.onNodeWithTag("portrait_detail").assertExists()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun dd() {
        val landscapeWindowSize = DpSize(720.dp, 360.dp)
        val fakeWindowSize = WindowSizeClass.calculateFromSize(landscapeWindowSize)
        composeTestRule.setContent {
            ProductStateless(
                product = fakeProduct,
                windowSizeClass = fakeWindowSize,
                onBack = {}
            )
        }

        composeTestRule.onNodeWithTag("landscape_detail").assertExists()
    }
}