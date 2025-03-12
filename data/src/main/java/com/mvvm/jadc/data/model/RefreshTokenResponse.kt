package com.mvvm.jadc.data.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("access_token") val token: String,
    @SerializedName("refresh_token") val refreshToken: String
)