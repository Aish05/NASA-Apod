package com.aish.apod.common

import com.aish.apod.enums.ErrorCodes
import com.aish.apod.model.ResponseError
import com.aish.apod.util.DataError
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * Util to handle the errors
 */
object ErrorUtils {
    /**
     * parses error body from server and converts to data class
     */
    inline fun <reified T> getErrorFromJson(
        input: Class<T>,
        ex: ResponseBody
    ): DataError.AppError? {
        ex.charStream().readText().let { data ->
            val myJson = MoshiInstance.getMoshiJson(
                input,
                JSONObject(data)
            )
            return myJson?.let {
                toDataError(it)
            }
        }
    }

    /**
     * converts the error data class to app error
     */
    inline fun <reified T> toDataError(error: T): DataError.AppError {
        return when (error) {
            is ResponseError -> {
                when (error.code) {
                    400 -> DataError.AppError(
                        errorCode = ErrorCodes.INVALID_DATE,
                        message = error.msg
                    )
                    else -> DataError.AppError(
                        errorCode = ErrorCodes.INVALID_DATE,
                        message = error.msg
                    )
                }
            }
            else -> {
                DataError.AppError(
                    errorCode = ErrorCodes.UNKNOWN,
                    message = ErrorCodes.UNKNOWN.value
                )
            }
        }
    }
}
