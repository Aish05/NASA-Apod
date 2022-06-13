package com.aish.apod.ui.favorites

import androidx.lifecycle.ViewModel
import com.aish.apod.common.SafeMutableLiveData
import com.aish.apod.model.PictureData

/**
 * Represents view model for Favourite Detail
 */
class FavouriteDetailViewModel(favData: PictureData) : ViewModel() {

    private val state = SafeMutableLiveData(FavouriteDetailState(favData))
    val pictureData = state.distinct { it.pictureData }
    val isVideoResource = state.distinct { it.isVideoResource }
    val formattedDate = state.distinct { it.formattedDate }
}
