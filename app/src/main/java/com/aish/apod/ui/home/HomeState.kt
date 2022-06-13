package com.aish.apod.ui.home

import com.aish.apod.extensions.isVideoResource
import com.aish.apod.model.PictureData
import com.aish.apod.util.formatCurrentDate

/**
 * Represents data state for Picture of the day
 */
data class HomeState(
    val pictureData: PictureData? = null,
    val errorMessage: String? = null,
    val isFavorite: Boolean = false,
    val isNetworkConnected: Boolean  = true
) {
    val formattedDate = pictureData?.date?.formatCurrentDate()
    val isVideoResource = pictureData?.mediaType?.isVideoResource()

    fun setPictureData(pictureData: PictureData) = copy(pictureData = pictureData)

    fun setErrorMessage(errorMessage: String?) = copy(errorMessage = errorMessage)

    fun setIsFavorite(isFavorite: Boolean)  = copy(isFavorite = isFavorite)

    fun setIsNetworkConnected(isNetworkConnected: Boolean) = copy(isNetworkConnected = isNetworkConnected)
}