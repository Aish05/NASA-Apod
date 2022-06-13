package com.aish.apod

import android.app.Application
import com.aish.apod.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

/**
 * Application class for NASA's astronomy picture of the day
 */
class APODApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        configureDependencies()
    }

    private fun configureDependencies() {
        startKoin {
            androidContext(this@APODApplication)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repoModule,
                    persistenceModule,
                    appModule
                )
            )
        }
    }
}