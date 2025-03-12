package com.mvvm.jadc.data.base

import com.google.gson.annotations.SerializedName

sealed class ApiError {
    data object Network : ApiError()

    data object NotFound : ApiError()

    data object AccessDenied : ApiError()

    data object ServiceUnavailable : ApiError()

    data object Unknown : ApiError()

    data class Exception(val throwable: Throwable?) : ApiError()
}

data class CustomException(
    @SerializedName("message")
    val apiMessage: String,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("cause")
    val apiCause: List<String>
) : Exception()