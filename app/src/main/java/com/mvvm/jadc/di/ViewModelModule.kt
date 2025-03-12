package com.mvvm.jadc.di

import com.jadc.presenter.ui.product.ProductViewModel
import com.jadc.presenter.ui.search.SearchViewModel
import com.jadc.presenter.ui.splash.AuthorizationViewModel
import com.mvvm.jadc.domain.util.CoroutineDependency
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(getProductsUseCase = get())
    }
    viewModel {
        ProductViewModel(
            getProductDetailUseCase = get(),
            ioDispatcher = get(qualifier = named(CoroutineDependency.IO)),
            mainDispatcher = get(qualifier = named(CoroutineDependency.MAIN))
        )
    }
    viewModel {
        AuthorizationViewModel(
            remoteConfig = get(),
            ioDispatcher = get(qualifier = named(CoroutineDependency.IO)),
            mainDispatcher = get(qualifier = named(CoroutineDependency.MAIN))
        )
    }
}