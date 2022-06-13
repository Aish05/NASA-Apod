package com.aish.apod.data.remote

import com.aish.apod.model.PictureData
import com.aish.apod.util.ResultWrapper

/**
 * Interface to Access Data for NASA APOD
 */
interface NasaRepository {
    suspend fun getPicOfTheDay(date : String): ResultWrapper<PictureData>

    suspend fun onFavClicked(pictureData: PictureData?)

    suspend fun isFavorite(date: String): Boolean

    suspend fun getFavorites(): List<PictureData>

    suspend fun getRecentCachedPicture() :  PictureData?

    suspend fun deleteFavorite(pictureData: PictureData)
}