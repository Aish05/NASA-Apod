package com.aish.apod.data.remote

import com.aish.apod.data.db.FavoritesDao
import com.aish.apod.data.db.FavoritesEntity
import com.aish.apod.data.db.RecentPictureEntity
import com.aish.apod.data.db.RecentPicturesDao
import com.aish.apod.model.PictureData
import com.aish.apod.util.ResultWrapper
import com.aish.apod.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *  Repository Implementation to execute network requests and db operations for NASA Apod data
 */
class NasaRepositoryImpl(
    private val nasaRestAPI: NasaRestAPI,
    private val favoritesDao: FavoritesDao,
    private val recentPicturesDao: RecentPicturesDao
) : NasaRepository {

    override suspend fun getPicOfTheDay(date: String): ResultWrapper<PictureData> {
        return safeApiCall {
            nasaRestAPI.getPicOfTheDay(date = date)
                .also {
                    recentPicturesDao.deleteAndInsertInTransaction(
                        RecentPictureEntity(
                            date = date,
                            copyright = it.copyright,
                            mediaType = it.mediaType,
                            explanation = it.explanation,
                            title = it.title,
                            url = it.url,
                            serviceVersion = it.serviceVersion,
                            hdurl = it.hdurl
                        )
                    )
                }
        }
    }

    override suspend fun onFavClicked(pictureData: PictureData?) {
        withContext(Dispatchers.IO) {
            pictureData?.let {
                val favoritesEntity = FavoritesEntity(
                    date = it.date,
                    copyright = it.copyright,
                    mediaType = it.mediaType,
                    explanation = it.explanation,
                    title = it.title,
                    url = it.url,
                    serviceVersion = it.serviceVersion,
                    hdurl = it.hdurl
                )

                if (favoritesDao.isFavorite(pictureData.date) != 1) {
                    favoritesDao.addData(favoritesEntity)
                } else {
                    favoritesDao.delete(favoritesEntity)
                }
            }
        }
    }

    override suspend fun isFavorite(date: String): Boolean {
        return withContext(Dispatchers.IO) { favoritesDao.isFavorite(date) != 0 }
    }

    override suspend fun getFavorites(): List<PictureData> {
        return withContext(Dispatchers.IO) {
            favoritesDao.getFavorites().mapNotNull { entity ->
                entity?.let {
                    PictureData(
                        date = it.date,
                        copyright = it.copyright,
                        mediaType = it.mediaType,
                        hdurl = it.hdurl,
                        serviceVersion = it.serviceVersion,
                        explanation = it.explanation,
                        title = it.title,
                        url = it.url
                    )
                }
            }
        }
    }

    override suspend fun getRecentCachedPicture(): PictureData? {
        return withContext(Dispatchers.IO) {
            recentPicturesDao.getRecentPictureData().let { entity->
                entity?.let {
                    PictureData(
                        date = it.date,
                        copyright = it.copyright,
                        mediaType = it.mediaType,
                        hdurl = it.hdurl,
                        serviceVersion = it.serviceVersion,
                        explanation = it.explanation,
                        title = it.title,
                        url = it.url
                    )
                }
            }
        }
    }

    override suspend fun deleteFavorite(pictureData: PictureData) {
        withContext(Dispatchers.IO) {
            favoritesDao.delete(
                FavoritesEntity(
                    date = pictureData.date,
                    copyright = pictureData.copyright,
                    mediaType = pictureData.mediaType,
                    explanation = pictureData.explanation,
                    title = pictureData.title,
                    url = pictureData.url,
                    serviceVersion = pictureData.serviceVersion,
                    hdurl = pictureData.hdurl
                )
            )
        }
    }
}

