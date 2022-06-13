package com.aish.apod.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * format current date to format 13 June 2022
 */
fun String.formatCurrentDate() : String {
    val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val desiredFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val date = format1.parse(this)
    return desiredFormat.format(date)
}

/**
 * returns get current date
 */
fun getCurrentDate(): String {
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
}