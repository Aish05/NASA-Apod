package com.aish.apod.ui.favorites

import com.aish.apod.extensions.isVideoResource
import com.aish.apod.model.PictureData
import com.aish.apod.util.formatCurrentDate

/**
 * Represents data state class for Favourite Detail
 */
data class FavouriteDetailState(
    val pictureData: PictureData
) {
    val isVideoResource = pictureData.mediaType?.isVideoResource()
    val formattedDate = pictureData.date.formatCurrentDate()
}