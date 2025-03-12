package com.mvvm.jadc.domain.usecases

import com.mvvm.jadc.data.base.RemoteResult
import com.mvvm.jadc.data.repository.product.ProductRepository
import com.mvvm.jadc.domain.base.OperationResult
import com.mvvm.jadc.domain.base.UIError
import com.mvvm.jadc.domain.base.userError
import com.mvvm.jadc.domain.mapper.toProductDetailUI
import com.mvvm.jadc.domain.model.ProductDetailUI
import com.mvvm.jadc.domain.util.Authorization

class GetProductDetailUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: String): OperationResult<ProductDetailUI> {
        return when (val result = repository.product(Authorization.token, productId)) {
            is RemoteResult.Success -> {
                OperationResult.Success(result.data[0].body.toProductDetailUI())
            }
            is RemoteResult.Error -> {
                OperationResult.Error(userError(result.apiError))
            }
        }
    }
}