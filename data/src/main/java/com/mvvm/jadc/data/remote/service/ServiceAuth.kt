package com.mvvm.jadc.data.remote.service

import com.mvvm.jadc.data.model.RefreshTokenRequest
import com.mvvm.jadc.data.model.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceAuth {
    @POST("/oauth/token")
    suspend fun auth(
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Response<RefreshTokenResponse>
}