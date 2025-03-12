package com.jadc.presenter.ui.search

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.google.common.truth.Truth.assertThat
import com.mvvm.jadc.domain.base.OperationResult
import com.mvvm.jadc.domain.model.ProductUI
import com.mvvm.jadc.domain.usecases.GetProductsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private lateinit var sut: SearchViewModel

    private lateinit var getProductsUseCase: GetProductsUseCase

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        getProductsUseCase = mockk()
        sut = SearchViewModel(getProductsUseCase)
    }
    
    @Test
    fun search_items_success() = runTest {
        every { Log.isLoggable(any(), any()) } returns false
        coEvery { getProductsUseCase.invoke("q") } returns OperationResult.Success(pagedResult)

        val data: Flow<PagingData<ProductUI>> = sut.products

        sut.search("q")

        val snapshot = data.asSnapshot()

        assertThat(snapshot).containsExactlyElementsIn(items)
    }
}