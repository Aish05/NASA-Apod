package com.aish.apod.model

/**
 * Represents error for NASA picture of the day API
 */
data class ResponseError(
    val msg: String = "",
    val code: Int = 0,
    val serviceVersion: String = ""
)