package com.mvvm.jadc.di

import com.mvvm.jadc.domain.usecases.GetProductDetailUseCase
import com.mvvm.jadc.domain.usecases.GetProductsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductsUseCase(get()) }
    factory { GetProductDetailUseCase(get()) }
}