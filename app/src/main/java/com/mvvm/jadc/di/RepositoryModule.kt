package com.mvvm.jadc.di

import com.mvvm.jadc.data.repository.product.ProductRepository
import com.mvvm.jadc.data.repository.product.ProductRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}