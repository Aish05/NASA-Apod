package com.aish.apod.util

import com.aish.apod.enums.ErrorCodes
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

/**
 * Wrapper class for all types of errors
 */
sealed class DataError<out T : Any> {

    /**
     * Api Failure Response Error - Error due to an unexpected non 2xx Response
     */
    data class ApiError<T : Any>(
        val body: T,
        val code: Int,
        val error: Throwable
    ) : DataError<T>()

    /**
     * Exception to access Network
     */
    data class NetworkError(
        val error: Throwable
    ) : DataError<Nothing>()

    /**
     * Error due to cancellation of task
     */
    data class CancellationError(
        val error: CancellationException
    ) : DataError<Nothing>()


    /**
     * Unauthorised to access network
     */
    data class AuthorizationError(
        val message: String,
        val code: Int,
        val error: Throwable
    ) : DataError<Nothing>()


    /**
     * API Time Out Error
     */
    data class TimeoutError(
        val error: SocketTimeoutException
    ) : DataError<Nothing>()

    /**
     * Unknown exception
     */
    data class UnknownError(
        val error: Throwable
    ) : DataError<Nothing>()

    /**
     * API response Error
     */
    data class AppError(
        val errorCode: ErrorCodes,
        val message: String
    ) : DataError<Nothing>()
}
