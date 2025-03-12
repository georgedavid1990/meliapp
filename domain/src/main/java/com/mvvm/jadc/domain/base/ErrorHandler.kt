package com.mvvm.jadc.domain.base

import com.mvvm.jadc.data.base.ApiError

fun userError(apiError: ApiError): UIError {
   return when (apiError) {
       ApiError.AccessDenied -> UIError.ServerError
       ApiError.Network -> UIError.Network
       ApiError.NotFound -> UIError.NotResult
       ApiError.ServiceUnavailable -> UIError.ServerError
       ApiError.Unknown -> UIError.ServerError
       is ApiError.Exception -> UIError.Exception(apiError.throwable)
   }
}