package com.aish.apod.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Recent Pictures Entity
 */
@Entity(tableName = "recent_picture")
data class RecentPictureEntity(
    @PrimaryKey
    val date: String,
    val copyright: String? = null,
    @ColumnInfo(name = "media_type")
    val mediaType: String? = null,
    val explanation: String? = null,
    val title: String? = null,
    val url: String? = null,
    @ColumnInfo(name = "service_version")
    val serviceVersion: String? = null,
    val hdurl: String? = null
)
