package com.mvvm.jadc.data.base

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Provides a safe way to execute an API request
 * @param dispatcher A [CoroutineDispatcher] used to switch to a background thread before executing the API request.
 */
class SafeApiRequest(
    val dispatcher: CoroutineDispatcher
) {

    /**
     * Executes the API request safely and returns the result wrapped in an [RemoteResult] object.
     *

     * @param apiRequest A suspend function that performs the API request and returns the result.
     * @return An [RemoteResult] object that contains either the success result or the error result.
     */
    suspend fun <T> request(
        apiRequest: suspend () -> T
    ): RemoteResult<T> {
        return withContext(dispatcher) {
            try {
                RemoteResult.Success(apiRequest.invoke())
            } catch (throwable: Throwable) {
                RemoteResult.Error(getError(throwable as Exception))
            }
        }
    }

    /**
     * Executes the API request safely with a custom Exception in order to map the api errors.
     *
     * @param apiRequest A suspend function that performs the API request and returns the result.
     * @return An [RemoteResult] object that contains either the success result or the error result.
     */
    suspend inline fun <T, reified E : java.lang.Exception> requestWithCustomException(
        crossinline apiRequest: suspend () -> T
    ): RemoteResult<T> {
        return withContext(dispatcher) {
            try {
                RemoteResult.Success(apiRequest.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> RemoteResult.Error(ApiError.Network)
                    is HttpException -> {
                        val gson = Gson()
                        val error = convertErrorToClass<E>(gson, throwable.response()?.errorBody()?.string())
                        RemoteResult.Error(ApiError.Exception(error))
                    }

                    else -> {
                        RemoteResult.Error(ApiError.Unknown)
                    }
                }
            }
        }
    }

    inline fun <reified E : java.lang.Exception> convertErrorToClass(gson: Gson, errorBody: String?): Exception? {
        return try {
            gson.fromJson(errorBody, E::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
