package com.mvvm.jadc.data.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("grant_type") val grantType: String = "refresh_token",
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSecret: String,
    @SerializedName("refresh_token") val refreshToken: String
)
