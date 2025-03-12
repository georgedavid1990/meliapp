package com.jadc.presenter.ui.product

import com.google.common.truth.Truth.assertThat
import com.mvvm.jadc.domain.base.OperationResult
import com.mvvm.jadc.domain.base.UIError
import com.mvvm.jadc.domain.model.ProductDetailUI
import com.mvvm.jadc.domain.usecases.GetProductDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

class ProductViewModelTest {

    private lateinit var sut: ProductViewModel

    private lateinit var getProductDetailUseCase: GetProductDetailUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private val fakeProduct = ProductDetailUI(
        id = "MCO1",
        name = "fake product",
        pictures = emptyList(),
        price = 1000000.0,
        stock = 100,
        attributes = emptyList()
    )

    @Before
    fun setUp() {
        getProductDetailUseCase = mockk()
        sut = ProductViewModel(
            getProductDetailUseCase,
            testDispatcher,
            testDispatcher
        )
    }

    @Test
    fun request_product_detail_success() = runBlocking {
        coEvery { getProductDetailUseCase(fakeProduct.id) } returns OperationResult.Success(fakeProduct)
        sut.getProductDetail(fakeProduct.id)

        val loading = sut.loading.value
        val productResult = sut.productDetail.value

        assertThat(loading).isFalse()
        assertThat(productResult).isEqualTo(fakeProduct)
    }

    @Test
    fun request_product_detail_error() = runBlocking {
        coEvery { getProductDetailUseCase(fakeProduct.id) } returns OperationResult.Error(UIError.ServerError)
        sut.getProductDetail(fakeProduct.id)

        val loading = sut.loading.value
        val productResult = sut.productDetail.value

        assertThat(loading).isFalse()
        assertThat(productResult).isNull()
    }
}