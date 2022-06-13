package com.aish.apod.di

import com.aish.apod.model.PictureData
import com.aish.apod.ui.favorites.FavouriteDetailViewModel
import com.aish.apod.ui.favorites.FavouritesViewModel
import com.aish.apod.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin Service Locator for all ViewModels
 */
val viewModelModule = module {
    viewModel { HomeViewModel(nasaRepository = get()) }
    viewModel { FavouritesViewModel(nasaRepository = get()) }
    viewModel { (favPictureData: PictureData) -> FavouriteDetailViewModel(favPictureData) }
}