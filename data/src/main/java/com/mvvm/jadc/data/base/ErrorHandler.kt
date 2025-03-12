package com.mvvm.jadc.data.base

import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

fun getError(exception: Exception): ApiError {
    return when (exception) {
        is IOException -> ApiError.Network
        is HttpException -> getError(exception.code())
        else -> ApiError.Unknown
    }
}

fun getError(httpCode: Int = 0): ApiError {
    return when (httpCode) {
        // no cache found in case of no network, thrown by retrofit -> treated as network error
        HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> ApiError.Network
        // not found
        HttpURLConnection.HTTP_NOT_FOUND -> ApiError.NotFound
        // access denied
        HttpURLConnection.HTTP_FORBIDDEN -> ApiError.AccessDenied
        // unavailable service
        HttpURLConnection.HTTP_UNAVAILABLE -> ApiError.ServiceUnavailable
        // all the others will be treated as unknown error
        else -> ApiError.Unknown
    }
}