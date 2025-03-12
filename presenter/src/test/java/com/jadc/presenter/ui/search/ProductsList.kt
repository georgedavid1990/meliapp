package com.jadc.presenter.ui.search

import com.mvvm.jadc.domain.model.ProductUI
import com.mvvm.jadc.domain.pagination.PagedResult

val items = (0..20).map {
    ProductUI(
        id = "MCO1",
        name = "Product $it",
        thumbnailUrl = "",
        price = 0.0,
        stock = 1
    )
}

val pagedResult = PagedResult(
    results = items,
    total = 21,
    offset = 0,
    limit = 50
)