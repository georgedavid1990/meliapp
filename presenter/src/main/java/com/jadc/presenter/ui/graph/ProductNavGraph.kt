package com.jadc.presenter.ui.graph

sealed class ProductNavGraph(val route: String) {
    data object Search : ProductNavGraph("search")
    data object Detail : ProductNavGraph("detail")
}

val productNavGraphRoutes = listOf(
    ProductNavGraph.Search,
    ProductNavGraph.Detail
)