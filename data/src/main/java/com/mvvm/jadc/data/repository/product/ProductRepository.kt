package com.mvvm.jadc.data.repository.product

import com.mvvm.jadc.data.base.BaseResult
import com.mvvm.jadc.data.base.RemoteResult
import com.mvvm.jadc.data.model.Product
import com.mvvm.jadc.data.model.ProductDetail
import com.mvvm.jadc.data.model.ProductDetailItem

interface ProductRepository {
    suspend fun search(
        token: String,
        query: String,
        offset: Int,
        limit: Int
    ): RemoteResult<BaseResult<List<Product>>>

    suspend fun product(
        token: String,
        productId: String
    ): RemoteResult<List<ProductDetailItem>>
}