package com.aish.apod.di

import com.aish.apod.BuildConfig
import com.aish.apod.data.remote.NasaRestAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Koin Service Locator for all network related objects
 */
val networkModule = module {

    single { httpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideNasaRestApi(get(named("nasaAPI"))) }
    single(named("nasaAPI")) { provideRetrofit(get(), "https://api.nasa.gov/") }

}

/**
 * Create retrofit instance
 *
 * @param okHttpClient
 * @return
 */
fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {

    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create().asLenient()).build()
}

/**
 * To create OkHttpClient
 *
 * @param loggingInterceptor
 * @return [OkHttpClient]
 */
fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
): OkHttpClient {

    val httpClient = OkHttpClient().newBuilder()

    if (BuildConfig.DEBUG)
        httpClient.addInterceptor(loggingInterceptor)

    return httpClient.build()
}

/**
 * Add httpLoggingInterceptor
 * This will be useful for checking api request and response in LogCat (Debug Build)
 *
 * @return [HttpLoggingInterceptor]
 */
fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return loggingInterceptor
}

/**
 * To create API end point
 *
 * @param retrofit
 * @return [NasaRestAPI]
 */
fun provideNasaRestApi(retrofit: Retrofit): NasaRestAPI =
    retrofit.create(NasaRestAPI::class.java)
