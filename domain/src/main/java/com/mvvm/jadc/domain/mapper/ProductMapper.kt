package com.mvvm.jadc.domain.mapper

import com.mvvm.jadc.data.base.BaseResult
import com.mvvm.jadc.data.base.RemoteResult
import com.mvvm.jadc.data.model.Product
import com.mvvm.jadc.data.model.ProductDetail
import com.mvvm.jadc.domain.model.ProductDetailUI
import com.mvvm.jadc.domain.model.ProductUI
import com.mvvm.jadc.domain.pagination.PagedResult

fun Product.toProductUI(): ProductUI {
    return ProductUI(
        id = id,
        name = title,
        thumbnailUrl = thumbnail,
        price = price ?: 0.0,
        stock = availableQuantity ?: 0,
    )
}

fun RemoteResult.Success<BaseResult<List<Product>>>.toPagedResult(): PagedResult<ProductUI> {
    return PagedResult(
        total = data.paging.total,
        offset = data.paging.offset,
        limit = data.paging.limit,
        results = data.results.map { it.toProductUI() }
    )
}

fun ProductDetail.toProductDetailUI(): ProductDetailUI {
    return ProductDetailUI(
        id = id,
        name = title,
        pictures = pictures.map { it.url },
        price = price ?: 0.0,
        stock = availableQuantity ?: 0,
        attributes = attributes.map { "${it.name}: ${it.valueName}" }
    )
}