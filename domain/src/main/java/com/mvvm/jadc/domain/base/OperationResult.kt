package com.mvvm.jadc.domain.base

sealed class OperationResult<out T> {
    data class Success<T>(val data: T): OperationResult<T>()
    data class Error(val uiError: UIError): OperationResult<Nothing>()
}