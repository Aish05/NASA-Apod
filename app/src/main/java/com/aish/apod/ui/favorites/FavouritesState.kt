package com.aish.apod.ui.favorites

import com.aish.apod.model.PictureData

/**
 * Represents data state class for Favourites
 */
data class FavouritesState(
    val pictureData:List<PictureData> = emptyList()
) {
    fun setPictureData(pictureData: List<PictureData>) = copy(pictureData = pictureData)
}