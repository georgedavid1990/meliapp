package com.mvvm.jadc.data.remote.product

import com.mvvm.jadc.data.base.BaseResult
import com.mvvm.jadc.data.base.CustomException
import com.mvvm.jadc.data.base.RemoteResult
import com.mvvm.jadc.data.base.SafeApiRequest
import com.mvvm.jadc.data.model.Product
import com.mvvm.jadc.data.model.ProductDetailItem
import com.mvvm.jadc.data.remote.service.ServiceMCO

class ProductRemoteDataSourceImp(
    private val serviceMCO: ServiceMCO,
    private val safeApiRequest: SafeApiRequest
): ProductRemoteDataSource {

    override suspend fun search(
        token: String,
        query: String,
        offset: Int,
        limit: Int
    ): RemoteResult<BaseResult<List<Product>>> {
        return safeApiRequest.requestWithCustomException<BaseResult<List<Product>>, CustomException> {
            serviceMCO.search(
                token,
                query,
                offset,
                limit
            )
        }
    }

    override suspend fun product(
        token: String,
        productId: String
    ): RemoteResult<List<ProductDetailItem>> {
        return safeApiRequest.requestWithCustomException<List<ProductDetailItem>, CustomException> {
            serviceMCO.product(
                token,
                productId
            )
        }
    }
}