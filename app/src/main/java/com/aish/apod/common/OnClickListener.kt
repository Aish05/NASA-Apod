package com.aish.apod.common

import com.aish.apod.model.PictureData

/**
 * interface for handling onclick event of recycler row item
 */
interface OnClickListener {
    fun onItemClick(pictureData : PictureData)

    fun onDeleteFav(pictureData : PictureData)
}