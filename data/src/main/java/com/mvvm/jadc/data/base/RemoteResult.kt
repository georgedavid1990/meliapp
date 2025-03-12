package com.mvvm.jadc.data.base

sealed class RemoteResult<out T> {
    data class Success<T>(val data: T): RemoteResult<T>()
    data class Error(val apiError: ApiError): RemoteResult<Nothing>()
}