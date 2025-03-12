package com.jadc.presenter.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jadc.presenter.ui.widget.LoadingSpinner
import com.jadc.presenter.ui.widget.NoInternet
import com.jadc.presenter.ui.widget.NoResult
import com.jadc.presenter.ui.widget.ProductCard
import com.jadc.presenter.ui.widget.SearchBar
import com.jadc.presenter.ui.widget.StartSearching
import com.jadc.presenter.utils.ConnectionObserverActivity
import com.jadc.presenter.utils.ConnectivityObserver
import com.mvvm.jadc.domain.model.ProductUI
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    onClick: (ProductUI) -> Unit,
    searchViewModel: SearchViewModel = koinViewModel()
) {
    val products = searchViewModel.products.collectAsLazyPagingItems()
    val query by searchViewModel.query.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context as ConnectionObserverActivity
    val connectionStatus = activity.connectivityObserver.observe().collectAsState(
        initial = activity.connectivityObserver.connectionStatus
    )

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        content = { padding ->
            Column (
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                SearchBar(
                    value = query,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    onValueChange = { q ->
                        searchViewModel.search(q)
                    }
                )

                if (connectionStatus.value == ConnectivityObserver.NetworkStatus.Available) {
                    if (query.isEmpty()) {
                        StartSearching()
                    } else {
                        when (products.loadState.refresh) {
                            is LoadState.Loading -> LoadingSpinner()
                            is LoadState.NotLoading -> {
                                if (products.itemCount > 0) {
                                    ProductsResult(
                                        products = products,
                                        onClick = onClick,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                } else {
                                    NoResult()
                                }
                            }
                            is LoadState.Error -> NoResult()
                        }
                    }
                } else {
                    NoInternet()
                }
            }
        }
    )
}

@Composable
private fun ProductsResult(
    products: LazyPagingItems<ProductUI>,
    onClick: (ProductUI) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products.itemCount) { index ->
            products[index]?.let { product ->
                ProductCard(
                    productUI = product,
                    onCardClick = { onClick(product) }
                )
            }
        }

        item {
            AnimatedVisibility(true) {
                LoadingSpinner()
            }
        }
    }
}