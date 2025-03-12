package com.mvvm.jadc.di

import com.mvvm.jadc.domain.util.CoroutineDependency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineModule = module {
    single<CoroutineDispatcher>(named(CoroutineDependency.IO)) { Dispatchers.IO }
    single<CoroutineDispatcher>(named(CoroutineDependency.MAIN)) { Dispatchers.Main }
}