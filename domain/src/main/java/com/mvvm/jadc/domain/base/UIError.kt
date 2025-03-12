package com.mvvm.jadc.domain.base

sealed class UIError {
    data object Network : UIError()
    data object ServerError : UIError()
    data object NotResult : UIError()
    data class Exception(val throwable: Throwable?) : UIError()
}