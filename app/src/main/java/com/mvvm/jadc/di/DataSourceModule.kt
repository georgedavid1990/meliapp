package com.mvvm.jadc.di

import com.mvvm.jadc.data.remote.product.ProductRemoteDataSource
import com.mvvm.jadc.data.remote.product.ProductRemoteDataSourceImp
import org.koin.dsl.module

val dataSourceModule = module {
    single<ProductRemoteDataSource> {
        ProductRemoteDataSourceImp(
            get(),
            get()
        )
    }
}