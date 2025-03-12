package com.mvvm.jadc.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import org.koin.dsl.module

val remoteConfigModule = module {
    single<FirebaseRemoteConfig> {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder().apply {
            minimumFetchIntervalInSeconds = 3600
            fetchTimeoutInSeconds = 60
        }.build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig
    }
}