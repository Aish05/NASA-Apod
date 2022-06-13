package com.aish.apod.util

import com.aish.apod.common.ErrorUtils
import com.aish.apod.model.ResponseError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

/**
 * Execute suspend calls in IO thread and wrap the response in [ResultWrapper]
 */
suspend fun <T : Any> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            wrapException<T>(throwable)
        }
    }
}

/**
 * Catch Exception from Retrofit calls
 */
private fun <T : Any> wrapException(ex: Throwable) = when (ex) {
    is HttpException -> {
        when (ex.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                ResultWrapper.Error(
                    DataError.AuthorizationError(
                        ex.message(),
                        ex.code(),
                        ex
                    )
                )
            }
            else -> {
                ex.response()?.errorBody()?.let {
                    try {
                        ErrorUtils.getErrorFromJson(ResponseError::class.java, it)?.let { error ->
                            ResultWrapper.Error(error)
                        }
                    } catch (exception: Exception) {
                        ResultWrapper.Error(DataError.ApiError(ex.message(), ex.code(), ex))
                    }
                } ?: ResultWrapper.Error(DataError.ApiError(ex.message(), ex.code(), ex))
            }
        }
    }
    is SocketTimeoutException -> {
        ResultWrapper.Error(DataError.TimeoutError(ex))
    }
    is IOException -> {
        ResultWrapper.Error(DataError.NetworkError(ex))
    }
    is CancellationException -> {
        ResultWrapper.Error(DataError.CancellationError(ex))
    }
    else -> {
        ResultWrapper.Error(DataError.UnknownError(ex))
    }
}
