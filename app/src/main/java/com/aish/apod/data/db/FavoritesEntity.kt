package com.aish.apod.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Favourites entity
 */
@Entity(tableName = "favorites")
data class FavoritesEntity(
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
