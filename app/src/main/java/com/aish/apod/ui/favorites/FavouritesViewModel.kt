package com.aish.apod.ui.favorites

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aish.apod.common.SafeMutableLiveData
import com.aish.apod.data.remote.NasaRepositoryImpl
import com.aish.apod.model.PictureData
import kotlinx.coroutines.launch

/**
 * Represents view model for Favourites
 */
class FavouritesViewModel(private val nasaRepository: NasaRepositoryImpl) : ViewModel() {

    private val state = SafeMutableLiveData(FavouritesState())
    val favList = state.distinct { it.pictureData }
    val showLoading = ObservableBoolean()

    init {
        viewModelScope.launch {
            showLoading.set(true)
            getFavorites()
        }
    }

    /**
     * remove the data from favorites
     */
    fun deleteFav(pictureData: PictureData) = viewModelScope.launch {
        showLoading.set(true)
        nasaRepository.deleteFavorite(pictureData)
        getFavorites()
    }

    /**
     * get list of all favorites
     */
    private suspend fun getFavorites() {
        val favList = nasaRepository.getFavorites()
        state.update { it.setPictureData(favList) }
        showLoading.set(false)
    }
}