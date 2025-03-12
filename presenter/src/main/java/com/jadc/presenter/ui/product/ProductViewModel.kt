package com.jadc.presenter.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.jadc.domain.base.OperationResult
import com.mvvm.jadc.domain.model.ProductDetailUI
import com.mvvm.jadc.domain.usecases.GetProductDetailUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ProductViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _productDetail = MutableStateFlow<ProductDetailUI?>(null)
    val productDetail: StateFlow<ProductDetailUI?> = _productDetail

    fun getProductDetail(productId: String) {
        _loading.update { true }
        viewModelScope.launch(ioDispatcher) {
            when(val result = getProductDetailUseCase(productId)) {
                is OperationResult.Success -> {
                    withContext(mainDispatcher) {
                        Timber.i("product detail: ${result.data.name}")
                        _loading.update { false }
                        _productDetail.update { result.data }
                    }
                }
                is OperationResult.Error -> {
                    withContext(mainDispatcher) {
                        Timber.e("product detail: ${result.uiError}")
                        _loading.update { false }
                        _productDetail.update { null }
                    }
                }
            }
        }
    }
}