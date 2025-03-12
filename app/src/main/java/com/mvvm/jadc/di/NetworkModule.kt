package com.mvvm.jadc.di

import com.mvvm.jadc.base.MeliAuthenticator
import com.mvvm.jadc.data.base.SafeApiRequest
import com.mvvm.jadc.data.remote.service.ServiceAuth
import com.mvvm.jadc.data.remote.service.ServiceMCO
import com.mvvm.jadc.domain.util.CoroutineDependency
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 1L
private const val READ_TIMEOUT = 20L
private const val BASE_URL = "https://api.mercadolibre.com"

val networkModule = module {

    single { SafeApiRequest(get(qualifier = named(CoroutineDependency.IO))) }

    single {
        OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            followRedirects(true)
            authenticator(MeliAuthenticator(BASE_URL))
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }.build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ServiceMCO> { get<Retrofit>().create(ServiceMCO::class.java) }

    single<ServiceAuth> { get<Retrofit>().create(ServiceAuth::class.java) }
}