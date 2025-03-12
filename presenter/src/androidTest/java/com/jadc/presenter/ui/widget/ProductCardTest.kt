package com.jadc.presenter.ui.widget

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mvvm.jadc.domain.model.ProductUI
import com.mvvm.jadc.domain.util.formatCurrency
import org.junit.Rule
import org.junit.Test
import java.util.Locale

class ProductCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeProduct = ProductUI(
        id = "MCO1",
        name = "fake product",
        thumbnailUrl = "",
        price = 1000000.0,
        stock = 100
    )

    @Test
    fun product_price_is_formatted_correctly() {
        composeTestRule.setContent {
            ProductCard(
                productUI = fakeProduct,
                onCardClick = {}
            )
        }

        val priceText = fakeProduct.price.formatCurrency(Locale.US)
        composeTestRule.onNodeWithText(priceText).assertExists()
    }
}