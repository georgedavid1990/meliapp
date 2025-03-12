package com.mvvm.jadc.domain.usecases

import com.mvvm.jadc.data.base.RemoteResult
import com.mvvm.jadc.data.repository.product.ProductRepository
import com.mvvm.jadc.domain.base.OperationResult
import com.mvvm.jadc.domain.base.userError
import com.mvvm.jadc.domain.mapper.toPagedResult
import com.mvvm.jadc.domain.model.ProductUI
import com.mvvm.jadc.domain.pagination.PagedResult
import com.mvvm.jadc.domain.pagination.PaginationConfig
import com.mvvm.jadc.domain.util.Authorization

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        query: String,
        offset: Int = 0,
        limit: Int = PaginationConfig.pageSize
    ): OperationResult<PagedResult<ProductUI>> {
        if (query.isEmpty()) {
            return OperationResult.Success(PagedResult.empty())
        }
        return when (val result = repository.search(Authorization.token, query, offset, limit)) {
            is RemoteResult.Success -> {
                OperationResult.Success(result.toPagedResult())
            }
            is RemoteResult.Error -> {
                OperationResult.Error(userError(result.apiError))
            }
        }
    }
}