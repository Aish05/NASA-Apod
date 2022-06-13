package com.aish.apod.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Represents data for NASA picture of the day
 */
@Keep
@Parcelize
@JsonClass(generateAdapter = true)
data class PictureData(
    @Json(name = "date")
    val date: String,
    @Json(name = "copyright")
    val copyright: String? = null,
    @Json(name = "media_type")
    val mediaType: String? = null,
    @Json(name = "hdurl")
    val hdurl: String? = null,
    @Json(name = "service_version")
    val serviceVersion: String? = null,
    @Json(name = "explanation")
    val explanation: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "url")
    val url: String? = null
) : Parcelable