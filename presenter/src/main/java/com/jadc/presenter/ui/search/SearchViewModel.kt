package com.jadc.presenter.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.mvvm.jadc.domain.base.OperationResult
import com.mvvm.jadc.domain.model.ProductUI
import com.mvvm.jadc.domain.pagination.PaginationConfig
import com.mvvm.jadc.domain.usecases.GetProductsUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private var queryDeferred: Deferred<PagingSource.LoadResult<Int, ProductUI>>? = null
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val products = _query.flatMapLatest {
        Pager(
            config = PagingConfig(
                pageSize = PaginationConfig.pageSize,
                prefetchDistance = PaginationConfig.prefetch
            ),
            pagingSourceFactory = { pagingSource() }
        ).flow.cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun search(query: String) {
        _query.update { query }
    }

    private fun pagingSource() =
        object : PagingSource<Int, ProductUI>() {
            override fun getRefreshKey(state: PagingState<Int, ProductUI>): Int {
                return 0
            }
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductUI> {
                val page = params.key ?: 0
                val offset = page * PaginationConfig.pageSize
                val limit = PaginationConfig.pageSize
                queryDeferred?.cancel()
                queryDeferred = viewModelScope.async {
                    return@async when(val result = getProductsUseCase(query.value, offset, limit)) {
                        is OperationResult.Error -> {
                            Timber.e(result.uiError.toString())
                            LoadResult.Error(Exception(result.uiError.toString()))
                        }
                        is OperationResult.Success -> {
                            Timber.i("total items:${result.data.total}")
                            LoadResult.Page(
                                data = result.data.results,
                                prevKey = if (page == 0) null else page - 1,
                                nextKey = if (result.data.results.isEmpty()) null else page + 1
                            )
                        }
                    }
                }
                return queryDeferred!!.await()
            }
        }
}