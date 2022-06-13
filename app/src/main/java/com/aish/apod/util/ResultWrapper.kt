package com.aish.apod.util

/**
 * Resource Wrapper class for data/errors passed to the app layer
 */
sealed class ResultWrapper<out R> {

    /**
     * Represents a successful response.
     */
    data class Success<out R>(val value: R) : ResultWrapper<R>()

    /**
     * Represents a error response
     */
    data class Error<T : Any>(val error: DataError<T>) : ResultWrapper<Nothing>()
}