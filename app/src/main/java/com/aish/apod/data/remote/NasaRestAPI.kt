package com.aish.apod.data.remote

import com.aish.apod.model.PictureData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API for NASA Apod data
 * //TODO move API key to secure place
 */
interface NasaRestAPI {

    @GET("planetary/apod")
    suspend fun getPicOfTheDay(
        @Query("api_key") apiKey: String = "gvZL22frFhMAn5crmL9Y1y1bCSuODEf4JAKbt0af",
        @Query("date") date: String,
    ): PictureData

}