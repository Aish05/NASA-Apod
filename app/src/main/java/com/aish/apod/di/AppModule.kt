package com.aish.apod.di

import android.app.Application
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Service Locator for all app related Utils
 */
val appModule = module {
    single { provideConnectivityManager(androidApplication()) }
}

/**
 * Returns [ConnectivityManager] instance
 */
private fun provideConnectivityManager(application: Application) =
    application.applicationContext.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
