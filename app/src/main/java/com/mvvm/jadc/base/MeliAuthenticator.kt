package com.mvvm.jadc.base

import com.mvvm.jadc.data.model.RefreshTokenRequest
import com.mvvm.jadc.data.model.RefreshTokenResponse
import com.mvvm.jadc.data.remote.service.ServiceAuth
import com.mvvm.jadc.domain.util.Authorization
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MeliAuthenticator(
    private val baseUrl: String
) : Authenticator {

    override fun authenticate(
        route: Route?,
        response: Response
    ): Request {
        synchronized(this) {
            val refreshTokenResult = runBlocking { refreshToken() }
            refreshTokenResult?.let {
                val newAccessToken = "Bearer ${refreshTokenResult.token}"
                Authorization.token = newAccessToken
                Authorization.refreshToken = refreshTokenResult.refreshToken
                return response.request.newBuilder()
                    .header("Authorization", newAccessToken)
                    .build()
            }
            return response.request
        }
    }

    private suspend fun refreshToken(): RefreshTokenResponse? {
        return try {
            val httpClient =
                OkHttpClient.Builder().apply {
                    retryOnConnectionFailure(true)
                }

            val retrofit =
                Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val refreshTokenApi = retrofit.create(ServiceAuth::class.java)
            val refreshTokenRequest = RefreshTokenRequest(
                clientId = Authorization.clientId,
                clientSecret = Authorization.clientSecret,
                refreshToken = Authorization.refreshToken
            )
            val result = refreshTokenApi.auth(refreshTokenRequest)
            if (result.isSuccessful && result.body() != null) {
                result.body()!!
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}