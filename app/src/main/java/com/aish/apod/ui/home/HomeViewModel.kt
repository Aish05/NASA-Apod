package com.aish.apod.ui.home

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aish.apod.common.SafeMutableLiveData
import com.aish.apod.common.SingleLiveEvent
import com.aish.apod.data.remote.NasaRepositoryImpl
import com.aish.apod.util.DataError
import com.aish.apod.util.onError
import com.aish.apod.util.onSuccess
import kotlinx.coroutines.launch

/**
 * Represents view model for Picture of the day home screen
 */
class HomeViewModel(private val nasaRepository: NasaRepositoryImpl) : ViewModel() {

    private val state = SafeMutableLiveData(HomeState())
    val pictureData = state.distinct { it.pictureData }
    val isVideoResource = state.distinct { it.isVideoResource }
    val isFavorite = state.distinct { it.isFavorite }
    val showToast = SingleLiveEvent<String>()
    val formattedDate = state.distinct { it.formattedDate }
    val showLoading = ObservableBoolean()

    /**
     * fetches the picture data from API/db
     */
    fun fetchPictureData(dateTime: String) = viewModelScope.launch {
        showLoading.set(true)
        if (state.value.isNetworkConnected) {
            nasaRepository.getPicOfTheDay(dateTime).onSuccess {
                state.update { s -> s.setPictureData(it) }
                checkIsFavorite()
            }.onError {
                getRecentCachedPictures()
                handleErrors(it)
            }.also {
                showLoading.set(false)
            }
        } else {
            showToast.postValue("You are offline")
            showLoading.set(false)
            getRecentCachedPictures()
        }
    }

    /**
     * handle fav fab click
     */
    fun onFavClicked() {
        viewModelScope.launch {
            state.value.pictureData?.let { pictureData ->
                nasaRepository.onFavClicked(pictureData)
                checkIsFavorite()
            }
        }
    }

    /**
     * sets the network connectivity status
     */
    fun setIsConnected(isConnected: Boolean) {
        state.update { s -> s.setIsNetworkConnected(isConnected) }
    }

    /**
     * checks if the current picture data is already fav
     */
    private fun checkIsFavorite() = viewModelScope.launch {
        state.value.pictureData?.date?.let { date ->
            val isFavourite = nasaRepository.isFavorite(date)
            state.update { s -> s.setIsFavorite(isFavourite) }
        }
    }

    /**
     * handles different errors
     */
    private fun handleErrors(error: DataError<*>) {
        when (error) {
            is DataError.AppError -> {
                showToast.postValue(error.message)
                state.update { s -> s.setErrorMessage(error.message) }
            }
            else -> {
                //no-op
                Log.d("Error", "$error")
            }
        }
    }

    /**
     * fetches last updated cached picture data
     */
    private suspend fun getRecentCachedPictures() {
        nasaRepository.getRecentCachedPicture()?.let {
            state.update { s -> s.setPictureData(it) }
            checkIsFavorite()
        }
    }
}