package com.aish.apod.enums

/**
 * Enum to represent server error codes
 */
enum class ErrorCodes(val value: String) {
    // Specific Error codes returned in Rest API Response as String
    INVALID_DATE("Operation returned error"),
    UNKNOWN("UNK")
}
