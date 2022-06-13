package com.aish.apod.di

import android.app.Application
import androidx.room.Room
import com.aish.apod.data.db.ApodDatabase
import org.koin.dsl.module

/**
 * Koin Service Locator for all data saving related objects
 */
val persistenceModule = module {

    single {
        provideDatabase(get())
    }

    single {
        provideFavoritesDao(get())
    }

    single {
        provideRecentPictureDao(get())
    }
}



private fun provideDatabase(application: Application) =
    Room.databaseBuilder(
        application,
        ApodDatabase::class.java,
        "database-apod"
    ).build()

private fun provideFavoritesDao(database: ApodDatabase) = database.favouritesDao()

private fun provideRecentPictureDao(database: ApodDatabase) = database.recentPictureDao()

