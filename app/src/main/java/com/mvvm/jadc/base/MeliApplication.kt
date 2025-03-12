package com.mvvm.jadc.base

import android.app.Application
import com.google.firebase.FirebaseApp
import com.mvvm.jadc.di.coroutineModule
import com.mvvm.jadc.di.dataSourceModule
import com.mvvm.jadc.di.networkModule
import com.mvvm.jadc.di.remoteConfigModule
import com.mvvm.jadc.di.repositoryModule
import com.mvvm.jadc.di.useCaseModule
import com.mvvm.jadc.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MeliApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@MeliApplication)
            modules(
                listOf(
                    coroutineModule,
                    networkModule,
                    dataSourceModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    remoteConfigModule
                )
            )
        }
    }
}