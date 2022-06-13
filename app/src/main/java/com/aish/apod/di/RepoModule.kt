package com.aish.apod.di

import com.aish.apod.data.remote.NasaRepositoryImpl
import org.koin.dsl.module

/**
 * Koin Service Locator for all repository related objects
 */
val repoModule = module {
    factory { NasaRepositoryImpl(get(), get(), get()) }
}