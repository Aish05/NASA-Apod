package com.aish.apod.util

/**
 * Function to run an callback when the ResultWrapper is Success. Callback is not called if the response is an Error.
 */
inline fun <T : Any> ResultWrapper<T>.onSuccess(callback: (value: T) -> Unit): ResultWrapper<out T> {
    if (this is ResultWrapper.Success) {
        callback(value)
    }
    return this
}

/**
 * Function to run an action when the ResultWrapper is Error. Callback is not called if the response is a Success.
 */
inline fun <T : Any> ResultWrapper<T>.onError(action: (DataError<*>) -> Unit): ResultWrapper<T> {
    if (this is ResultWrapper.Error<*> && this.error !is DataError.CancellationError) {
        action(this.error)
    }
    return this
}